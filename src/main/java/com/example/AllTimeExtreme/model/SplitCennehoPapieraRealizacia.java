package com.example.AllTimeExtreme.model;

import com.example.AllTimeExtreme.model.kluce.SplitCennehoPapieraRealizaciaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="SPLIT_CENNEHO_PAPIERA_REALIZACIA")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SplitCennehoPapieraRealizacia {

    @EmbeddedId
    SplitCennehoPapieraRealizaciaPK pk;

}
