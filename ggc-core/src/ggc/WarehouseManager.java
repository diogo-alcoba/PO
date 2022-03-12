package ggc;

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.TreeMap;
import ggc.exceptions.*;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current store. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  
  /** 
   * @return the filename
   */
  public String get_filename(){
    return _filename;
  }

  
  /** 
   * @return true if there is no filename
   */
  public boolean isFilenameEmpty(){
    return _filename.equals("");
  }

  public TreeMap<String, Product> getProduct_list(){
    return _warehouse.getProduct_list();
  }
  
  /** 
   * @return the date
   */
  public int showDate(){
    return _warehouse.showDate();
  }

  
  /** 
   * @param days
   * @throws coreInvalidDateException
   */
  public void advanceDate(int days) throws coreInvalidDateException{
    _warehouse.advanceDate(days);
  }

  
  /** 
   * @return accounting balance
   */
  public double showAccountingBalance(){
    return _warehouse.showAccountingBalance();
  }
  
  
  /** 
   * @return available balance
   */
  public double showAvailableBalance(){
    return _warehouse.showAvailableBalance();
  }

  
  /** 
   * @param key
   * @param name
   * @param address
   * @throws coreDuplicatePartnerKeyException
   */
  public void registerPartner(String key, String name, String address) throws coreDuplicatePartnerKeyException, coreUnknownPartnerKeyException, coreUnknownProductKeyException{
    _warehouse.registerPartner(key, name, address);
  }

  
  /** 
   * @param key
   * @param price
   * @param stock
   * @param multiplicate_aggravation
   * @param components
   */
  public void registerProduct(String key, double price, int stock, double multiplicate_aggravation, String components){
    _warehouse.registerProduct(key, price, stock, multiplicate_aggravation, components);
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
    _warehouse.registerBatch(product_key, partner_key, price, current_stock, multiplicate_aggravation, components);
  }

  
  /** 
   * @param key
   * @return a string describing the wanted partner
   * @throws coreUnknownPartnerKeyException
   */
  public String showPartner(String key) throws coreUnknownPartnerKeyException{
    return _warehouse.showPartner(key);
  }

  
  /** 
   * @return list (string) of all registered partners
   */
  public String showAllPartners(){
    return _warehouse.showAllPartners();
  }

  
  /** 
   * @return list (string) with all known products
   */
  public String showAllProducts(){
    return _warehouse.showAllProducts();
  }

  
  /** 
   * @return list (string) with all available batches
   */
  public String showAvailableBatches(){
    return _warehouse.showAvailableBatches();
  }

  public String showBatchesByPartner(String partner_key) throws coreUnknownPartnerKeyException{
    return _warehouse.showBatchesByPartner(partner_key);
  }

  public String showBatchesByProduct(String product_key) throws coreUnknownProductKeyException{
    return _warehouse.showBatchesByProduct(product_key);
  }

  public void registerAcquisition(String partner_key, String product_key, int amount, double payed_value) throws coreUnknownProductKeyException, coreUnknownPartnerKeyException{
    _warehouse.registerAcquisition(partner_key, product_key, amount, payed_value);
  }

  public void registerSale(String partner_key, String product_key, int amount, int deadline) throws coreUnknownPartnerKeyException, coreUnknownProductKeyException, coreUnavailableProductException{
    _warehouse.registerSale(partner_key, product_key, amount, deadline);
  }

  public void registerBreakdown(String partner_key, String product_key, int amount) throws coreUnknownPartnerKeyException, coreUnknownProductKeyException, coreUnavailableProductException{
    _warehouse.registerBreakdown(partner_key, product_key, amount);
  }

  public String showTransaction(int transaction_number) throws coreUnknownTransactionKeyException{
    return _warehouse.showTransaction(transaction_number);
  }

  public String showPartnerAcquisitions(String partner_key) throws coreUnknownPartnerKeyException{
    return _warehouse.showPartnerAcquisitions(partner_key);
  }

  public String showPartnerSales(String partner_key) throws coreUnknownPartnerKeyException{
    return _warehouse.showPartnerSales(partner_key);
  }

  public void toggleProductNotification(String partner_key, String product_key) throws coreUnknownProductKeyException, coreUnknownPartnerKeyException{
    _warehouse.toggleProductNotification(partner_key, product_key);
  }

  public String LookupProductBatchesUnderGivenPrice(double price){
    return _warehouse.LookupProductBatchesUnderGivenPrice(price);
  }

  public String LookupPaymentsByPartner(String partner_key) throws coreUnknownPartnerKeyException{
    return _warehouse.LookupPaymentsByPartner(partner_key);
  }

  public void receivePayment(int transaction_key) throws coreUnknownTransactionKeyException{
    _warehouse.receivePayment(transaction_key);
  }

  public String showCheapestProduct(){
    return _warehouse.showCheapestProduct();
  }

  public String showLeastOrderValuePartner(){
    return _warehouse.showLeastOrderValuePartner();
  }

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
    out.writeObject(_warehouse);
    out.close();  
  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {
    ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
    _warehouse = (Warehouse) in.readObject();
    in.close();
    _filename = filename;
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
	    _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException/* FIXME maybe other exceptions */ e) {
	      throw new ImportFileException(textfile);
    }
  }

}
