/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author User
 */
public class partyTypeModel {

    private final String section;
    private final String name;

    public partyTypeModel(String section, String name) {
        this.section = section;
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public String getName() {
        return name;
    }

}
