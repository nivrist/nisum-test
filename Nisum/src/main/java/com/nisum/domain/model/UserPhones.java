package com.nisum.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author irvin
 */
@Getter
@Setter
@Entity
@Table(name = "user_phones")
public class UserPhones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "contry_code")
    private String contryCode;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    private Users idUser;
}