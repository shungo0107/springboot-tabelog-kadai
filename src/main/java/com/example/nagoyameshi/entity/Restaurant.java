package com.example.nagoyameshi.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "restaurants")
@Data
public class Restaurant {
	/*Id　⇒　主キー
	 * GeneratedValue　⇒　テーブル内のAUTO_INCREMENTを指定したカラム（idカラム）を
	 * 　　　　　　　　　　　利用して値を生成するようになります。つまり、データの作成時や
	 * 　　　　　　　　　　　更新時にidの値を自分で指定しなくても、自動採番されるようになるということです。
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 
 
    @Column(name = "name")
    private String name;
    
    @Column(name = "image_name")
    private String imageName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    /*  insertable、updatable 属性（値を挿入/更新できるか）の初期値はtrue。
     *  falseに設定することで、アプリ側からはカラムに値を挿入したり更新したりできなくなります。
     *  逆にいえば、カラムに挿入または更新する値の管理をデータベース側に任せられるということです。
     */
    @Column(name = "create_date", insertable = false, updatable = false)
    private Timestamp createDate;

    @Column(name = "update_date", insertable = false, updatable = false)
    private Timestamp updateDate;
}
