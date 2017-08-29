package com.vmcop.simplefour.monanngon.model;
import com.google.gson.annotations.SerializedName;

public class BeanPost {
    @SerializedName("title")
    private String title;
	@SerializedName("nguyen_lieu")
    private String nguyen_lieu;
    @SerializedName("cach_lam")
    private String cach_lam;
    @SerializedName("image_name")
    private String image_name;
    @SerializedName("nguon_tk")
    private String nguon_tk;
    @SerializedName("ngay_tao")
    private String ngay_tao;
    
	public BeanPost(String title, String nguyen_lieu, String cach_lam, String image_name, String nguon_tk, String ngay_tao) {
        this.title = title;
        this.nguyen_lieu = nguyen_lieu;
        this.cach_lam = cach_lam;
        this.image_name = image_name;
        this.nguon_tk = nguon_tk;
        this.ngay_tao = ngay_tao;
    }
	
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNguyen_lieu() {
		return nguyen_lieu;
	}
	public void setNguyen_lieu(String nguyen_lieu) {
		this.nguyen_lieu = nguyen_lieu;
	}
	public String getCach_lam() {
		return cach_lam;
	}
	public void setCach_lam(String cach_lam) {
		this.cach_lam = cach_lam;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public String getNguon_tk() {
		return nguon_tk;
	}
	public void setNguon_tk(String nguon_tk) {
		this.nguon_tk = nguon_tk;
	}
	public String getNgay_tao() {
		return ngay_tao;
	}
	public void setNgay_tao(String ngay_tao) {
		this.ngay_tao = ngay_tao;
	}
}
