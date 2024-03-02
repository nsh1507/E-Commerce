package com;
// Source code is decompiled from a .class file using FernFlower decompiler.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.persistence.HelperFileDAO;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@Tag("Persistence-tier")
public class HelperFileDAOTest {
   HelperFileDAO helperFileDAO;
   Helper[] testHelpers;
   ObjectMapper mockObjectMapper;

   public HelperFileDAOTest() {
   }

   @BeforeEach
   public void setupHelperFileDAO() throws IOException {
      this.mockObjectMapper = (ObjectMapper)Mockito.mock(ObjectMapper.class);
      this.testHelpers = new Helper[3];
      this.testHelpers[0] = new Helper(99, "Wi-Fire", 1, "Cookies1234", false);
      this.testHelpers[1] = new Helper(100, "Galactic Agent", 2, "Chips3456", false);
      this.testHelpers[2] = new Helper(101, "Ice Gladiator", 3, "Pizza5678", false);
      Mockito.when((Helper[])this.mockObjectMapper.readValue(new File("doesnt_matter.txt"), Helper[].class)).thenReturn(this.testHelpers);
      this.helperFileDAO = new HelperFileDAO("doesnt_matter.txt", this.mockObjectMapper);
   }

   @Test
   public void testGetHelpers() {
      Helper[] helpers = this.helperFileDAO.getHelpers();
      Assertions.assertEquals(helpers.length, this.testHelpers.length);

      for(int i = 0; i < this.testHelpers.length; ++i) {
         Assertions.assertEquals(helpers[i], this.testHelpers[i]);
      }

   }

   @Test
   public void testFindHelpers() {
      Helper[] helpers = this.helperFileDAO.findHelpers("la");
      Assertions.assertEquals(helpers.length, 2);
      Assertions.assertEquals(helpers[0], this.testHelpers[1]);
      Assertions.assertEquals(helpers[1], this.testHelpers[2]);
   }

   @Test
   public void testGetHelper() {
      Helper helper = this.helperFileDAO.getHelper(99);
      Assertions.assertEquals(helper, this.testHelpers[0]);
   }

   @Test
   public void testDeleteHelper() {
      boolean result = (Boolean)Assertions.assertDoesNotThrow(() -> {
         return this.helperFileDAO.deleteHelper(99);
      }, "Unexpected exception thrown");
      Assertions.assertEquals(result, true);
      Assertions.assertEquals(this.helperFileDAO.getHelpers().length, this.testHelpers.length - 1);
   }

   @Test
   public void testCreateHelper() {
      Helper helper = new Helper(102, "Wonder-Person", 4, "Pasta7890", false);
      Helper result = (Helper)Assertions.assertDoesNotThrow(() -> {
         return this.helperFileDAO.createHelper(helper);
      }, "Unexpected exception thrown");
      Assertions.assertNotNull(result);
      Helper actual = this.helperFileDAO.getHelper(helper.getId());
      Assertions.assertEquals(actual.getId(), helper.getId());
      Assertions.assertEquals(actual.getName(), helper.getName());
   }

   @Test
   public void testUpdateHelper() {
      Helper helper = this.testHelpers[1] = new Helper(100, "Galactic Agent", 2, "Chips3456", false);
      Helper result = (Helper)Assertions.assertDoesNotThrow(() -> {
         return this.helperFileDAO.updateHelpers(helper);
      }, "Unexpected exception thrown");
      Assertions.assertNotNull(result);
      Helper actual = this.helperFileDAO.getHelper(helper.getId());
      Assertions.assertEquals(actual, helper);
   }

   @Test
   public void testSaveException() throws IOException {
      ((ObjectMapper)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockObjectMapper)).writeValue((File)ArgumentMatchers.any(File.class), ArgumentMatchers.any(Helper[].class));
      Helper helper = new Helper(99, "Wi-Fire", 1, "Cookies1234", false);
      Assertions.assertThrows(IOException.class, () -> {
         this.helperFileDAO.createHelper(helper);
      }, "IOException not thrown");
   }

   @Test
   public void testGetHelperNotFound() {
      Helper helper = this.helperFileDAO.getHelper(98);
      Assertions.assertEquals(helper, (Object)null);
   }

   @Test
   public void testDeleteHelperNotFound() {
      boolean result = (Boolean)Assertions.assertDoesNotThrow(() -> {
         return this.helperFileDAO.deleteHelper(98);
      }, "Unexpected exception thrown");
      Assertions.assertEquals(result, false);
      Assertions.assertEquals(this.helperFileDAO.getHelpers().length, this.testHelpers.length);
   }

   @Test
   public void testUpdateHelperNotFound() {
      Helper helper = new Helper(98, "Bolt", 5, "Bolt1234", false);
      Helper result = (Helper)Assertions.assertDoesNotThrow(() -> {
         return this.helperFileDAO.updateHelpers(helper);
      }, "Unexpected exception thrown");
      Assertions.assertNull(result);
   }

   @Test
   public void testConstructorException() throws IOException {
      ObjectMapper mockObjectMapper = (ObjectMapper)Mockito.mock(ObjectMapper.class);
      ((ObjectMapper)Mockito.doThrow(new Throwable[]{new IOException()}).when(mockObjectMapper)).readValue(new File("doesnt_matter.txt"), Helper[].class);
      Assertions.assertThrows(IOException.class, () -> {
         new HelperFileDAO("doesnt_matter.txt", mockObjectMapper);
      }, "IOException not thrown");
   }
}

