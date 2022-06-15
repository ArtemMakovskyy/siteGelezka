package com.example.mag0422.excel.brain;


import com.example.mag0422.dao.brain.ReadBrainDb;

import com.example.mag0422.entity.brain.Brain;

import java.util.List;

public class RedXl {

    public static void main(String[] args) {
        ReadBrainDb readBrainDb = new ReadBrainDb();
        List<Brain>brainList = readBrainDb.readXlsBrain();
        System.out.println(brainList.get(1));

    }

}
