package com.ufund.api.persistance;
// Source code is decompiled from a .class file using FernFlower decompiler.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedFileDAO;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@Tag("Persistence-tier")
public class NeedFileDAOTest {
   NeedFileDAO needFileDAO;
   Need[] testNeeds;
   ObjectMapper mockObjectMapper;

   public NeedFileDAOTest() {
   }

   @BeforeEach
   public void setupNeedFileDAO() throws IOException {
      this.mockObjectMapper = (ObjectMapper)Mockito.mock(ObjectMapper.class);
      this.testNeeds = new Need[3];
      this.testNeeds[0] = new Need(99, "Wi-Fire", 0, 0, null);
      this.testNeeds[1] = new Need(100, "Galactic Agent", 0, 0, null);
      this.testNeeds[2] = new Need(101, "Ice Gladiator", 0, 0, null);
      Mockito.when((Need[])this.mockObjectMapper.readValue(new File("doesnt_matter.txt"), Need[].class)).thenReturn(this.testNeeds);
      this.needFileDAO = new NeedFileDAO("doesnt_matter.txt", this.mockObjectMapper);
   }

   @Test
   public void testGetNeeds() {
      Need[] needs = this.needFileDAO.getNeeds();
      Assertions.assertEquals(needs.length, this.testNeeds.length);

      for(int i = 0; i < this.testNeeds.length; ++i) {
         Assertions.assertEquals(needs[i], this.testNeeds[i]);
      }

   }

   @Test
   public void testFindNeeds() {
      Need[] needs = this.needFileDAO.findNeeds("la");
      Assertions.assertEquals(needs.length, 2);
      Assertions.assertEquals(needs[0], this.testNeeds[1]);
      Assertions.assertEquals(needs[1], this.testNeeds[2]);
   }

   @Test
   public void testGetNeed() {
      Need need = this.needFileDAO.getNeed(99);
      Assertions.assertEquals(need, this.testNeeds[0]);
   }

   @Test
   public void testDeleteNeed() {
      boolean result = (Boolean)Assertions.assertDoesNotThrow(() -> {
         return this.needFileDAO.deleteNeed(99);
      }, "Unexpected exception thrown");
      Assertions.assertEquals(result, true);
      Assertions.assertEquals(this.needFileDAO.getNeeds().length, this.testNeeds.length - 1);
   }

   @Test
   public void testCreateNeed() {
      Need need = new Need(102, "Wonder-Person", 0, 0, null);
      Need result = (Need)Assertions.assertDoesNotThrow(() -> {
         return this.needFileDAO.createNeed(need);
      }, "Unexpected exception thrown");
      Assertions.assertNotNull(result);
      Need actual = this.needFileDAO.getNeed(need.getId());
      Assertions.assertEquals(actual.getId(), need.getId());
      Assertions.assertEquals(actual.getName(), need.getName());
   }

   @Test
   public void testUpdateNeed() {
      Need need = new Need(99, "Galactic Agent", 0, 0, null);
      Need result = (Need)Assertions.assertDoesNotThrow(() -> {
         return this.needFileDAO.updateNeed(need);
      }, "Unexpected exception thrown");
      Assertions.assertNotNull(result);
      Need actual = this.needFileDAO.getNeed(need.getId());
      Assertions.assertEquals(actual, need);
   }

   @Test
   public void testSaveException() throws IOException {
      ((ObjectMapper)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockObjectMapper)).writeValue((File)ArgumentMatchers.any(File.class), ArgumentMatchers.any(Need[].class));
      Need need = new Need(102, "Wi-Fire", 0, 0, null);
      Assertions.assertThrows(IOException.class, () -> {
         this.needFileDAO.createNeed(need);
      }, "IOException not thrown");
   }

   @Test
   public void testGetNeedNotFound() {
      Need need = this.needFileDAO.getNeed(98);
      Assertions.assertEquals(need, (Object)null);
   }

   @Test
   public void testDeleteNeedNotFound() {
      boolean result = (Boolean)Assertions.assertDoesNotThrow(() -> {
         return this.needFileDAO.deleteNeed(98);
      }, "Unexpected exception thrown");
      Assertions.assertEquals(result, false);
      Assertions.assertEquals(this.needFileDAO.getNeeds().length, this.testNeeds.length);
   }

   @Test
   public void testUpdateNeedNotFound() {
      Need need = new Need(98, "Bolt", 0, 0, null);
      Need result = (Need)Assertions.assertDoesNotThrow(() -> {
         return this.needFileDAO.updateNeed(need);
      }, "Unexpected exception thrown");
      Assertions.assertNull(result);
   }

   @Test
   public void testConstructorException() throws IOException {
      ObjectMapper mockObjectMapper = (ObjectMapper)Mockito.mock(ObjectMapper.class);
      ((ObjectMapper)Mockito.doThrow(new Throwable[]{new IOException()}).when(mockObjectMapper)).readValue(new File("doesnt_matter.txt"), Need[].class);
      Assertions.assertThrows(IOException.class, () -> {
         new NeedFileDAO("doesnt_matter.txt", mockObjectMapper);
      }, "IOException not thrown");
   }
}
