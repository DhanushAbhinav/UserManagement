package com.example.usermanagement.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Manager {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID manager_id;

     private String name;
     private boolean is_active = true;

     // Getters and Setters
     public UUID getManager_id() {
          return manager_id;
     }

     public void setManager_id(UUID manager_id) {
          this.manager_id = manager_id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public boolean isIs_active() {
          return is_active;
     }

     public void setIs_active(boolean is_active) {
          this.is_active = is_active;
     }
}
