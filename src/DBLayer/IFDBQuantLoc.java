package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBQuantLoc
 * @author Group 3/C
 */
public interface IFDBQuantLoc {
	// get all QuantLocs of Product
	public ArrayList<QuantLoc> getProductQLs(Product product, boolean retriveAssociation) throws Exception;
	// get all QuantLocs of Product
	public ArrayList<QuantLoc> getStateQLs(ProductState state, boolean retriveAssociation) throws Exception;
	//get one QuantLoc by its id
	public QuantLoc findQuantLoc(int id, boolean retriveAssociation) throws Exception;
	//get one QuantLoc by Product
	public QuantLoc findQuantLoc(Product product, boolean retriveAssociation) throws Exception;
	//get one QuantLoc by ProductState
	public QuantLoc findQuantLoc(ProductState state, boolean retriveAssociation) throws Exception;


	//insert a new QuantLoc of Product
	public int insertQuantLoc(QuantLoc quantLoc, Product product) throws Exception;
	//insert a new QuantLoc of ProductState
	public int insertQuantLoc(QuantLoc quantLoc, ProductState state) throws Exception;
	//update QuantLoc of Product
	public int updateQuantLoc(QuantLoc quantLoc, Product product) throws Exception;
	//update QuantLoc of ProductState
	public int updateQuantLoc(QuantLoc quantLoc, ProductState state) throws Exception;

}
