package com.lutheroaks.tacoswebsite.kb;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Embeddable;

import com.lutheroaks.tacoswebsite.member.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KBPostKey implements Serializable {
    
    private Timestamp timeStamp;

    private transient Member member;
}


