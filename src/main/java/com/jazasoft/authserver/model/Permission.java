package com.jazasoft.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity class stores  various Application specific dynamic permissions to be granted to user
 *
 */
@NoArgsConstructor
@Data
@Entity
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Since, There may be one or many special permission required by any application.
     * So, this field specifies which permission.
     * e.g: For TNA Application, value of this field may be [buyer, activity]
     */
    @Column(nullable = false)
    private String key;

    /**
     * Id of Permission that will be included in access token.
     * e.g: For TNA app, value of this field can be buyer id [Id of Buyer Entity in TNA app] if key is buyer
     * {
     *     ...
     *     "buyer": "1,2,3"
     *     "activity": "3,8"
     *     ...
     * }
     */
    @Column(nullable = false)
    private String permissionId;

    /**
     * Display Name of the Entity which will be used by auth server application while associating any user with entity
     * e.g: Dressmann, Hacket, Fabric Launch
     */
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private App app;

    @ManyToOne
    private User user;
}
