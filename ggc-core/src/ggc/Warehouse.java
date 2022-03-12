package ggc;

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import ggc.exceptions.*;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  private double accounting_balance;
  private double available_balance;
  private int date;
  private int transaction_number;
  private TreeMap<String, Partner> partner_list;
  private TreeMap<String, Product> product_list;
  private TreeMap<String, DerivedProduct> derived_product_list;
  private ArrayList<Transaction> transaction_list;
  private TreeMap<Integer,Acquisition> acquisition_list;
  private TreeMap<Integer, Sale> sale_list;
  private TreeMap<Integer, Breakdown> breakdown_list;


  
  public Warehouse(){
    accounting_balance = 0;
    available_balance = 0;
    date = 0;
    transaction_number = 0;
    partner_list = new TreeMap<String, Partner>(String.CASE_INSENSITIVE_ORDER);
    product_list = new TreeMap<String, Product>(String.CASE_INSENSITIVE_ORDER);
    derived_product_list = new TreeMap<String, DerivedProduct>(String.CASE_INSENSITIVE_ORDER);
    transaction_list = new ArrayList<Transaction>();
    acquisition_list = new TreeMap<Integer, Acquisition>();
    sale_list = new TreeMap<Integer, Sale>();
    breakdown_list = new TreeMap<Integer, Breakdown>();
  }

  /**
   * 
   * @param days
   * @throws coreInvalidDateException
   */

  public TreeMap<String, Product> getProduct_list(){
    return product_list;
  }


  public void advanceDate(int days) throws coreInvalidDateException{
    if (days <= 0){
      throw new coreInvalidDateException(Integer.toString(days));
    }
    else{
      this.date += days;
    }
  }

  
  /** 
   * @return the date
   */
  public int showDate(){
    return this.date;
  }

  
  /** 
   * @return accounting balance
   */
  public double showAccountingBalance(){
    return this.accounting_balance;
  }

  
  /** 
   * @return available balance
   */
  public double showAvailableBalance(){
    return this.available_balance;
  }

  
  /** 
   * @param key
   * @param name
   * @param address
   * @throws coreDuplicatePartnerKeyException
   */
  public void registerPartner(String key, String name, String address) throws coreDuplicatePartnerKeyException, coreUnknownPartnerKeyException, coreUnknownProductKeyException{
    if (partner_list.get(key) != null){
      throw new coreDuplicatePartnerKeyException(key);
    }
    Partner partner = new Partner(key, name, address);
    partner_list.put(key, partner);
    for (Product product : product_list.values()){
      toggleProductNotification(key, product.getKey());
    }
  }

  
  /** 
   * @param key
   * @param price
   * @param stock
   * @param multiplicate_aggravation
   * @param components
   */
  public void registerProduct(String key, double price, int stock, double multiplicate_aggravation, String components){
    if (product_list.get(key) != null){
      if (price > product_list.get(key).getMax_price()){
        product_list.get(key).setMax_price(price);
      }
      if (price < product_list.get(key).getMin_price()){
        product_list.get(key).setMin_price(price);
        product_list.get(key).notifyObservers("BARGAIN", price);
      }
      product_list.get(key).setTotal_current_stock(product_list.get(key).getTotal_current_stock() + stock);
    }
    else{
      if (multiplicate_aggravation == 0){ //see if its a simple product or not
        Product product = new Product(key, price, price, stock);
        product_list.put(key, product);
        for (Partner partner : partner_list.values()){
          product.registerObserver(partner.getKey(), partner);
        }
      }
      else{
        DerivedProduct derived_product = new DerivedProduct(key, price, price, stock, multiplicate_aggravation, components);
        String[] individual_components = components.split("#"); //parse the components of the derived product
        for (String ind_cmp : individual_components){
            String[] name_and_quantity = ind_cmp.split(":");
            if (product_list.get(name_and_quantity[0]) == null){ //check to see if the components are already known
              //throw new coreUnknownProductKeyException(name_and_quantity[0]);
            }
            else{
              derived_product.addComponent(product_list.get(name_and_quantity[0]), Integer.parseInt(name_and_quantity[1]));
            }
        }
        product_list.put(key, derived_product);
        derived_product_list.put(key, derived_product);
        for (Partner partner : partner_list.values()){
          derived_product.registerObserver(partner.getKey(), partner);
        }
      }
    }
  }

  
  /** 
   * @param product_key
   * @param partner_key
   * @param price
   * @param current_stock
   * @param multiplicate_aggravation
   * @param components
   */
  public void registerBatch(String product_key, String partner_key, double price, int current_stock, double multiplicate_aggravation, String components){
    Batch batch = new Batch(product_key, partner_key, price, current_stock);
    registerProduct(product_key, price, current_stock, multiplicate_aggravation, components);
    //batch_list.put(product_key, batch);
    product_list.get(product_key).addBatch(batch);
    partner_list.get(partner_key).addBatch(batch);
  }

  
  /** 
   * @param key
   * @return a string describing the wanted partner
   * @throws coreUnknownPartnerKeyException
   */
  public String showPartner(String key) throws coreUnknownPartnerKeyException{
    if (partner_list.get(key) == null){ 
      throw new coreUnknownPartnerKeyException(key);
    }
    else{
      String res = "";
      Partner partner = partner_list.get(key);
      res += partner.toString();
      if (!partner.getNotifications().isEmpty()){
        res += "\n";
        for (Notification notification : partner.getNotifications()){
          res += notification.toString() + "\n";
        }
        partner.getNotifications().clear();
        res = res.substring(0, res.length() - 1);
      }
      return res;
    }
  }

  
  /** 
   * @return list (string) of all registered partners
   */
  public String showAllPartners(){
    String res = "";
    if (partner_list.isEmpty()){
      return res;
    }
    for (Map.Entry<String, Partner> entry : partner_list.entrySet()){ 
      res += entry.getValue().toString() + "\n";
    }
    res = res.substring(0, res.length() - 1);
    return res;
  }

  
  /** 
   * @return list (string) with all known products
   */
  public String showAllProducts(){
    String res = "";
    if (product_list.isEmpty()){
      return res;
    }
    for (Map.Entry<String, Product> entry : product_list.entrySet()){ 
      res += entry.getValue().toString() + "\n";
    }
    res = res.substring(0, res.length() - 1);
    return res;
  }

  
  /** 
   * @return list (string) with all available batches
   */
  public String showAvailableBatches(){
    String res = "";
    for (Map.Entry<String, Product> product_entry : product_list.entrySet()){ 
      if (!product_entry.getValue().getBatchesByProduct().isEmpty()){
          Collections.sort(product_entry.getValue().getBatchesByProduct(), new BatchSortingComparator());
          for (Batch batch : product_entry.getValue().getBatchesByProduct()){
            res += batch.toString() + "\n";
          }
      } 
    }
    if (!res.equals("")){
      res = res.substring(0, res.length() - 1);
    }
    return res;
  }

  public String showBatchesByPartner(String partner_key) throws coreUnknownPartnerKeyException{
    if (partner_list.get(partner_key) == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    }
    String res = "";
    Partner partner = partner_list.get(partner_key);
    if (partner.getBatchesByPartner().isEmpty()){
      return res;
    }
    Collections.sort(partner.getBatchesByPartner(), new BatchSortingComparator());
    for (Batch batch : partner.getBatchesByPartner()){
      res += batch.toString() + "\n";
    }
    res = res.substring(0, res.length() - 1);
    return res;
  }

  public String showBatchesByProduct(String product_key) throws coreUnknownProductKeyException{
    if (product_list.get(product_key) == null){
      throw new coreUnknownProductKeyException(product_key);
    }
    String res = "";
    Product product = product_list.get(product_key);
    if (product.getBatchesByProduct().isEmpty()){
      return res;
    }
    Collections.sort(product.getBatchesByProduct(), new BatchSortingComparator());
    for (Batch batch : product.getBatchesByProduct()){
      res += batch.toString() + "\n";
    }
    res = res.substring(0, res.length() - 1);
    return res;
  }



  public void registerAcquisition(String partner_key, String product_key, int amount, double price) throws coreUnknownProductKeyException, coreUnknownPartnerKeyException{
    if (partner_list.get(partner_key) == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    }
    if (product_list.get(product_key) == null){
      throw new coreUnknownProductKeyException(product_key);
    }
    Partner partner = partner_list.get(partner_key);
    Product product = product_list.get(product_key);
    double payed_value = amount * price;
    accounting_balance -= payed_value;
    available_balance -= payed_value;
    Acquisition acquisition = new Acquisition(transaction_number, partner_key, product_key, amount, payed_value, date);
    transaction_list.add(acquisition);
    acquisition_list.put(transaction_number, acquisition);
    transaction_number++;
    /* if (product.getTotal_current_stock() == 0){
      product.notifyObservers("NEW", price);
    } */
    registerBatch(product_key, partner_key, price, amount, 0, null); 
    partner.addTransaction(acquisition);
    partner.setOrders_value(partner.getOrders_value() + payed_value);
  }

  public double removeProduct(String partner_key, String product_key, int amount){
    Partner partner = partner_list.get(partner_key);
    Product product = product_list.get(product_key);
    product.setTotal_current_stock(product.getTotal_current_stock() - amount);
    Collections.sort(product.getBatchesByProduct(), new BatchPriceComparator());
    //int amount_copy = amount;
    double base_value = 0;
    for (int i = 0; (!product.getBatchesByProduct().isEmpty()) && (amount >= product.getBatchesByProduct().get(i).getCurrent_stock()); product.getBatchesByProduct().remove(i)){
      base_value += product.getBatchesByProduct().get(i).getCurrent_stock() * product.getBatchesByProduct().get(i).getPrice();
      amount -= product.getBatchesByProduct().get(i).getCurrent_stock();
    }
    if (!product.getBatchesByProduct().isEmpty()){
      base_value += amount * product.getBatchesByProduct().get(0).getPrice();
      product.getBatchesByProduct().get(0).setCurrent_stock(product.getBatchesByProduct().get(0).getCurrent_stock() - amount);
    }
    accounting_balance += base_value;
    return base_value;
  }

  public void registerSale(String partner_key, String product_key, int amount, int deadline) throws coreUnknownPartnerKeyException, coreUnknownProductKeyException, coreUnavailableProductException{
    Partner partner = partner_list.get(partner_key);
    Product product = product_list.get(product_key);
    if (partner == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    } 
    if (product == null){
      throw new coreUnknownProductKeyException(product_key);
    }
    if (amount > product.getTotal_current_stock()){
      throw new coreUnavailableProductException(product_key, amount, product.getTotal_current_stock());
    }
    double base_value = removeProduct(partner_key, product_key, amount);
    Sale sale = new Sale(transaction_number, partner_key, product_key, amount, base_value, base_value, deadline, -1);
    transaction_list.add(sale);
    sale_list.put(transaction_number, sale);
    transaction_number++;
    partner.addTransaction(sale);
    partner.setPerformed_sales_value(partner.getPerformed_sales_value() + base_value);
  }

  public void registerBreakdown(String partner_key, String product_key, int amount) throws coreUnknownPartnerKeyException, coreUnknownProductKeyException, coreUnavailableProductException{
    Partner partner = partner_list.get(partner_key);
    Product product = product_list.get(product_key);
    if (partner == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    } 
    if (product == null){
      throw new coreUnknownProductKeyException(product_key);
    }
    if (amount > product.getTotal_current_stock()){
      throw new coreUnavailableProductException(product_key, amount, product.getTotal_current_stock());
    }
    if (!derived_product_list.containsKey(product_key)){
      return;
    }
    DerivedProduct derived_product = derived_product_list.get(product_key);
    //if (partner != null && partner.get)
    double base_value = removeProduct(partner_key, product_key, amount);
    Breakdown breakdown = new Breakdown(transaction_number, partner_key, product_key, amount, base_value, base_value, date);
    transaction_list.add(breakdown);
    breakdown_list.put(transaction_number, breakdown);
    transaction_number++;
    partner.addTransaction(breakdown);
    String components_price = "";
    for (Product component : derived_product.getComponent_list().keySet()){
      double lowest_price = derived_product.getBatchesByProduct().get(0).getPrice();
      for (Batch batch : derived_product.getBatchesByProduct()){
        if (batch.getPrice() < lowest_price){
          lowest_price = batch.getPrice();
        }
      }
      components_price += component.getKey() + ":" + amount * derived_product.getComponent_list().get(component) + ":" + Math.round(lowest_price) + "#";  
    }
    components_price = components_price.substring(0, components_price.length() - 1);
    breakdown.setComponents_price(components_price);
  }

  public String showTransaction(int transaction_key) throws coreUnknownTransactionKeyException{
    if (transaction_key < 0 || transaction_key >= transaction_number){
      throw new coreUnknownTransactionKeyException(Integer.toString(transaction_key));
    }
    return transaction_list.get(transaction_key).toString();
  }
  
  public String showPartnerAcquisitions(String partner_key) throws coreUnknownPartnerKeyException{
    if (partner_list.get(partner_key) == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    }
    String res = "";
    Partner partner = partner_list.get(partner_key);
    for (Transaction transaction : partner.getTransactionHistory()){
      if (acquisition_list.containsKey(transaction.getId())){
        Acquisition acquisition = (Acquisition) transaction;
        res += acquisition.toString() + "\n";
      }
    }
    if (!res.equals("")){
      res = res.substring(0, res.length() - 1);
    }
    return res;
  }

  public String showPartnerSales(String partner_key) throws coreUnknownPartnerKeyException{
    if (partner_list.get(partner_key) == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    }
    String res = "";
    Partner partner = partner_list.get(partner_key);
    for (Transaction transaction : partner.getTransactionHistory()){
      if (sale_list.containsKey(transaction.getId())){
        Sale sale = (Sale) transaction;
        res += sale.toString() + "\n";
      }
      else if (breakdown_list.containsKey(transaction.getId())){
        Breakdown breakdown = (Breakdown) transaction;
        res += breakdown.toString() + "\n";
      }
    }
    if (!res.equals("")){
      res = res.substring(0, res.length() - 1);
    }
    return res;
  }

  public void toggleProductNotification(String partner_key, String product_key) throws coreUnknownPartnerKeyException, coreUnknownProductKeyException{

    if (partner_list.get(partner_key) == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    }
    if (product_list.get(product_key) == null){
      throw new coreUnknownProductKeyException(product_key);
    }
    Partner partner = partner_list.get(partner_key);
    Product product = product_list.get(product_key);

    if (product.hasObserver(partner_key)) {
        product.removeObserver(partner_key);
    }
    else {
        product.registerObserver(partner_key, partner);
    }
  }

  public String LookupProductBatchesUnderGivenPrice(double price){
    String res = "";
    for (Map.Entry<String, Product> product_entry : product_list.entrySet()){ 
      if (!product_entry.getValue().getBatchesByProduct().isEmpty()){
          Collections.sort(product_entry.getValue().getBatchesByProduct(), new BatchSortingComparator());
          for (Batch batch : product_entry.getValue().getBatchesByProduct()){
            if (batch.getPrice() < price){
            res += batch.toString() + "\n";
            }
          }
      } 
    }
    if (!res.equals("")){
      res = res.substring(0, res.length() - 1);
    }
    return res;
  }

  public String LookupPaymentsByPartner(String partner_key) throws coreUnknownPartnerKeyException{
    if (partner_list.get(partner_key) == null){
      throw new coreUnknownPartnerKeyException(partner_key);
    }
    String res = "";
    Partner partner = partner_list.get(partner_key);
    for (Transaction transaction : partner.getTransactionHistory()){
      if (sale_list.containsKey(transaction.getId())){
        Sale sale = (Sale) transaction;
        if (sale.getPayment_date() > -1){
          res += sale.toString() + "\n";
        }
      }
    }
    if (!res.equals("")){
      res = res.substring(0, res.length() - 1);
     }
     return res;
  }

  public void receivePayment(int transaction_key) throws coreUnknownTransactionKeyException{
    if (transaction_key < 0 || transaction_key >= transaction_number){
      throw new coreUnknownTransactionKeyException(Integer.toString(transaction_key));
    }
    if (!sale_list.containsKey(transaction_key)){
      return;
    }
    Sale sale = sale_list.get(transaction_key);
    if (sale.getPayment_date() > -1){
      return;
    }
    Product product = product_list.get(sale.getProduct_key());
    Partner partner = partner_list.get(sale.getPartner_key());
    int deadline = sale.getDeadline();
    double base_value = sale.getBase_value();
    double value_to_pay = base_value;
    int N;
    if (derived_product_list.containsKey(product.getKey())){
      N = 3;
    }
    else {
      N = 5;
    }
    if (deadline - date >= N){
      value_to_pay = partner.getStatus().payP1(base_value);
      partner.setPoints(partner.getPoints() + (int) Math.round(value_to_pay * 10));
      partner.setStatus(partner.getStatus().checkIfUpgrade(partner.getPoints()));
    }
    else if ((deadline - date >= 0) && (deadline - date < N)){
      value_to_pay = partner.getStatus().payP2(base_value, date, deadline);
      partner.setPoints(partner.getPoints() + (int) Math.round(value_to_pay * 10));
      partner.setStatus(partner.getStatus().checkIfUpgrade(partner.getPoints()));
    }
    else if ((date - deadline >= 0) && (date - deadline <= N)){
      value_to_pay = partner.getStatus().payP3(base_value, date, deadline);
      if (partner.getStatus().checkIfDowngrade(date - deadline).equals("NORMAL")){
        partner.setPoints((int) (0.1 * partner.getPoints()));
        partner.setStatus(new Normal());
      }
      else if (partner.getStatus().checkIfDowngrade(date - deadline).equals("SELECTION")){
        partner.setPoints((int) (0.25 * partner.getPoints()));
        partner.setStatus(new Selection());
      }
    }
    else if (date - deadline > N){
      value_to_pay = partner.getStatus().payP4(base_value, date, deadline);
      if (partner.getStatus().checkIfDowngrade(date - deadline).equals("NORMAL")){
        partner.setPoints((int) (0.1 * partner.getPoints()));
        partner.setStatus(new Normal());
      }
      else if (partner.getStatus().checkIfDowngrade(date - deadline).equals("SELECTION")){
        partner.setPoints((int) (0.25 * partner.getPoints()));
        partner.setStatus(new Selection());
      }
    }
    accounting_balance += value_to_pay - base_value;
    available_balance += value_to_pay;
    sale.setValue_to_pay(value_to_pay);
    sale.setPayment_date(date);
    partner.setPaid_sales_value(partner.getPaid_sales_value() + value_to_pay);
  }


  public String showCheapestProduct(){
    double lowest_price = -1;
    String cheapest_product = "";
    for (Product product : product_list.values()){
      double product_price_sum = 0;
      for (Batch batch : product.getBatchesByProduct()){
        product_price_sum += batch.getPrice();
      }
      if (lowest_price == -1){
        lowest_price = product_price_sum;
        cheapest_product = product.toString();
      }
      else if (product_price_sum < lowest_price){
        lowest_price = product_price_sum;
        cheapest_product = product.toString();
      }
    }
    return cheapest_product;
  }

  public String showLeastOrderValuePartner(){
    String res_partner = "";
    double lowest_value = -1;
    for (Partner partner : partner_list.values()){
      if (lowest_value == -1){
        lowest_value = partner.getOrders_value();
        res_partner = partner.toString();
      }
      else if (partner.getOrders_value() < lowest_value){
        lowest_value = partner.getOrders_value();
        res_partner = partner.toString();
      }
    }
    return res_partner;
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    String line;
    BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    while ((line = reader.readLine()) != null){
      String[] fields = line.split("\\|");
      Pattern patPartner = Pattern.compile("^(PARTNER)");
      Pattern patBatch_S = Pattern.compile("^(BATCH_S)");
      Pattern patBatch_M = Pattern.compile("^(BATCH_M)");
      if (patPartner.matcher(fields[0]).matches()){
        registerPartner(fields[1], fields[2], fields[3]);
      }
      else if (patBatch_S.matcher(fields[0]).matches()){
        registerBatch(fields[1], fields[2], Double.parseDouble(fields[3]), Integer.parseInt(fields[4]), 0, null);
      }
      else if(patBatch_M.matcher(fields[0]).matches()){ 
        registerBatch(fields[1], fields[2], Double.parseDouble(fields[3]), Integer.parseInt(fields[4]), Double.parseDouble(fields[5]), fields[6]);
      }
    }
    reader.close();
  }


}
