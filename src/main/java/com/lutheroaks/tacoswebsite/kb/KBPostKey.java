package com.lutheroaks.tacoswebsite.kb;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;

import com.lutheroaks.tacoswebsite.member.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class KBPostKey implements Serializable {
    
    private Date timeStamp;

    private transient Member member;
}


