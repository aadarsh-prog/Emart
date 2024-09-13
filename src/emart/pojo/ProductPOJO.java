package emart.pojo;

public class ProductPOJO 
{
    private String p_id;
    private String p_name;
    private String p_company;
    private double p_price;
    private double o_price;
    private byte p_tax;
    private int p_quantity;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
     public ProductPOJO()
     {
         
     }
    public ProductPOJO(String p_id, String p_name, String p_company, double p_price, double o_price, byte p_tax, int p_quantity) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_company = p_company;
        this.p_price = p_price;
        this.o_price = o_price;
        this.p_tax = p_tax;
        this.p_quantity = p_quantity;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_company() {
        return p_company;
    }

    public void setP_company(String p_company) {
        this.p_company = p_company;
    }

    public double getP_price() {
        return p_price;
    }

    public void setP_price(double p_price) {
        this.p_price = p_price;
    }

    public double getO_price() {
        return o_price;
    }

    public void setO_price(double o_price) {
        this.o_price = o_price;
    }

    public byte getP_tax() {
        return p_tax;
    }

    public void setP_tax(byte p_tax) {
        this.p_tax = p_tax;
    }

    public int getP_quantity() {
        return p_quantity;
    }

    public void setP_quantity(int p_quantity) {
        this.p_quantity = p_quantity;
    }
}
