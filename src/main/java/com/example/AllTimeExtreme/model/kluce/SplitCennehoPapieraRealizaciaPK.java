package com.example.AllTimeExtreme.model.kluce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SplitCennehoPapieraRealizaciaPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="ID_CENNEHO_PAPIERA")
    Integer idcp;

    @Column(name="REALIZOVANE")
    Date realizovane;

}
