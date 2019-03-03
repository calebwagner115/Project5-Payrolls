import org.junit.*; // Rule, Test
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.nio.IntBuffer;
import java.util.*;

import payroll.*;

public class PayrollTest {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("PayrollTest");
  }

  // 1 second max per method tested
  @Rule public Timeout globalTimeout = Timeout.seconds(1);

  // BEGIN TESTS HERE.

  // Eliminate this test after you get the hang of things

  @Test public void constructor1() {
    //make sure the empty string is empty and is represnted right
    Payroll n =  new Payroll();
    assertEquals(n.employees().toString(),"[]");

  }

  //checking the constructor
  @Test public void constructor2()throws UnsupportedOperationException{
    //EMpty dict
    Dict<String,Integer> d = new Dict<String,Integer>();
    //null dict
    Dict<String,Integer> rip = (Dict<String,Integer>) null;

    //see what happens if the constructor takes in a null dict in constructor (should fail
    try {
      Payroll nul = new Payroll(rip);
      //if the null passes with an error fail
      assertFalse(true);
    }

    catch(NullPointerException e){
      //did as it was supposed to.... error caught
      assertTrue(true);
    }
    //add bob
    d.put("BOB",2500);
    //make new payroll
    Payroll s = new Payroll( d);
    //check and see if the tostrings match
    assertEquals(s.employees().toString(),"[BOB]");

    //try and put in nulls
    try {
      d.put(null, null);
    }
    //should catch the null
    catch (UnsupportedOperationException e ){
      assertTrue(true);
    }

    //check the payroll employees
    Payroll pp = new Payroll(d);
      assertEquals(s.employees().toString(), "[BOB]");
  }

//see if looking at all the employees works
  @Test public void employees(){
    Payroll pay =  new Payroll();
    Dict<String,Integer> d = new Dict<String,Integer>();
    ArrayList<String> aList  = new ArrayList<>();

    //check the empty list
    assertEquals(pay.employees(), aList);
    //add people in the payroll
    d.put("BOB",50000);
    d.put("ali",9292);
    //add the people to the list
    Payroll pay2 = new Payroll(d);
    aList.add("BOB");
    aList.add("ali");
    //see of the lists are the same
    assertEquals(pay2.employees(),aList);

    //get rid of BOB
    d.pop("BOB");
    //remove it
    aList.remove(0);

    //check and see oif the employees are the same
    assertEquals(aList,pay2.employees());

//get rid of ali
    d.pop("ali");
    aList.remove(0);
    //after deleting check and see if the are the same
    assertEquals(aList,pay2.employees());
  }

  //firing an employee
  @Test public void fire(){
    //initialize
    Payroll pay=  new Payroll();
    Dict<String,Integer> d = new Dict<String,Integer>();
    ArrayList<String> aList  = new ArrayList<>();

    //see the list of employees
    assertEquals(pay.employees(), aList);
    //add two employeees
    d.put("BOB",50000);
    d.put("ali",9292);

    //create a seperate list to compare
    Payroll pay2 = new Payroll(d);
    aList.add("BOB");
    aList.add("ali");
    //see if the employee list matches what we made
    assertEquals(pay2.employees(),aList);


    //see if wee can remove Bob
    assertTrue(pay2.fire("BOB"));
    aList.remove(0);
    /see if bob is gone
    assertEquals(aList,pay2.employees());


    //remove everyone
    assertTrue(pay2.fire("ali"));
    assertFalse(pay2.fire("BOB"));

    aList.remove(0);
    assertEquals(aList,pay2.employees());
  }

  @Test public void getSalary(){
    Dict<String,Integer> d = new Dict<String,Integer>();

  //add employees
    d.put("BOB",50000);
    d.put("ali",9292);

    //new payroll to compare
    Payroll pay2 = new Payroll(d);

    //see if bob's salary is 50000
    assertEquals(pay2.getSalary("BOB"),50000);

    //find salary of null value
    try{
      assertEquals(pay2.getSalary(null),50000);
      assertTrue(false);
    }
    catch (NullPointerException e){
      assertTrue(true);
    }
    //fire bob
    pay2.fire("BOB");

    try{
      //try to find salary of missing person
      assertEquals(pay2.getSalary("BOB"),0);
      assertTrue(false);
    }
    catch (NoSuchElementException e){
      assertTrue(true);
    }

  }

//give a raise to everyone
  @Test public void giveRaiseEveryone(){
    Dict<String,Integer> d = new Dict<String,Integer>();
    int i;
    //add in employees
    d.put("BOB",100);
    d.put("ali",100);
    //new payroll to compare
    Payroll pay2 = new Payroll(d);
    //10% raise
    pay2.giveRaise(0.1);
    //new empty dict
    Dict<String,Integer> d2 = new Dict<String,Integer>();
    //the values we need
    d2.put("BOB",110);
    d2.put("ali",110);
    //create the payroll
    Payroll pay3 = new Payroll(d2);

    //see if the raises are properly applied to everyone
    assertEquals(pay2.getSalary("BOB"),pay3.getSalary("BOB"));
    assertEquals(pay2.getSalary("ali"),pay3.getSalary("ali"));

//fire everyone except ali
    pay2.fire("BOB");
    pay3.fire("BOB");
    pay3.fire("ali");
    //give ali a raise
    pay2.giveRaise(0.1);
    pay3.hire("ali",121);
    //see if ali got the raise correctly again
    assertEquals(pay3.getSalary("ali"),pay2.getSalary("ali"));

    //give someone a negative raise that should throw an exception
    try{
      pay2.giveRaise("ali",-12);
      assertTrue(false);
    }
    catch(RuntimeException e){
      assertTrue(true);
    }

  }

  //give one person a raise
  @Test public void giveRaise(){
    Dict<String,Integer> d = new Dict<String,Integer>();
    //add employees
    d.put("BOB",100);
    d.put("ali",1000);

    Payroll pay2 = new Payroll(d);
    //give bob raise
    pay2.giveRaise("BOB",.10);

    //give a raise to name that doesnt exist
    try{
      pay2.giveRaise("bub",0.2);
      assertTrue(false);
    }
    catch(NoSuchElementException e){
      assertTrue(true);
    }

    //get the salary after raise
    assertEquals(110,pay2.getSalary("BOB"));
    //try negative salary, should throw exception
    try{
      pay2.giveRaise("ali",-12);
      assertTrue(false);
    }
    catch(RuntimeException e){
      assertTrue(true);
    }
  }



  @Test public void hire(){
    Dict<String,Integer> d = new Dict<String,Integer>();
    //employees
    d.put("BOB",1000);
    d.put("ali",2000);

    Payroll pay2 = new Payroll(d);

    //actually hire them and check salary
    pay2.hire("Tony",3000);
    assertEquals(pay2.getSalary("Tony"),3000);

    //add bob and check sal
    pay2.hire("BOB",1);
    assertEquals(pay2.getSalary("BOB"),1);

    //try and hire a null, should throw exception
    try{
      pay2.hire(null,1);
      assertTrue(false);
    }
    catch(NullPointerException e){
      assertTrue(true);
    }

  }

  //the salarys are yearly and need to be / 12
  @Test public void monthlyExpense(){
    Dict<String,Integer> d = new Dict<String,Integer>();
    Dict<String,Integer> d2 = new Dict<String,Integer>();

    //add everyone
    Payroll pay2 = new Payroll();
    pay2.hire("BOB",100);
    pay2.hire("ali",10);
    pay2.hire("Tony",60);
    //check monthly
    assertEquals(15,pay2.monthlyExpense());
    //get rid of everyone
    pay2.fire("BOB");
    pay2.fire("ali");
    pay2.fire("Tony");

    //hire everyone with new salaries
    pay2.hire("BOB",1000);
    pay2.hire("ali",2000);
    pay2.hire("Tony",3000);
    //get ans
    int monthly = 6000/12;
    //check the monthly cost
    assertEquals(monthly,pay2.monthlyExpense());

    //empty returns 0
    Payroll empty = new Payroll();
    assertEquals(0,empty.monthlyExpense());

  }

  @Test public void topSalary(){
    Dict<String,Integer> d = new Dict<String,Integer>();

    d.put("BOB",50000);
    d.put("ali",9292);

    //empty returns 0
    Payroll pay = new Payroll();
    assertEquals(0,pay.topSalary());

    Payroll pay2 = new Payroll(d);

    //check max getSalary();
    assertEquals(50000,pay2.topSalary());
    pay2.fire("BOB");
    pay2.hire("BOB",9292);
    assertEquals(9292,pay2.topSalary());

  }


  //NOW IT IS TIME FOR DICT CLASS
  //DICT
  //DICT
  @Test public void clear() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    Dict<String,Integer> d2 = new Dict<String,Integer>();
    //see if after adding bob
    d.put("BOB",50000);
    d.clear();
    //the clear works? check size
    assertEquals(d.size(),d2.size());
  }

  @Test public void get() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    Dict<String,Integer> d2 = new Dict<String,Integer>();
    d.put("BOB",50000);

    //try get null
    try{
      d.get(null);
      assertTrue(false);
    }
    catch(NoSuchElementException e){
      assertTrue(true);
    }
    //try get DNE
    try{
      d.get("DNFSKF");
      assertTrue(false);
    }
    catch(NoSuchElementException e){
      assertTrue(true);
    }

    //check if bob ahs top
    d.put("BOB",50000);
    d.get("BOB");
    assertTrue(d.get("BOB")==50000);
  }


  @Test public void has() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    Dict<String,Integer> d2 = new Dict<String,Integer>();

    //check if bob is contained
    d.put("BOB",50000);
    assertTrue(d.has("BOB"));
    //remove bob and try again
    d.pop("BOB");
    assertFalse(d.has("BOB"));
  }

  @Test public void keys() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    ArrayList<String> key = new ArrayList<String>();

    //add bob
    d.put("BOB",50000);
    key.add("BOB");
    //see if bob is in there
    assertEquals((List) key,d.keys());
    d.pop("BOB");
    //clear all
    key.clear();
    //nothing exists
    assertEquals((List) key,d.keys());

  }

  @Test public void pop() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    ArrayList<String> key = new ArrayList<String>();

    //intizalize
    d.put("BOB",50000);
    d.put("DICT",60000);

    //see if the obj is removed and size changed
    assertTrue(d.size()==2);
    assertTrue(50000==d.pop("BOB"));
    assertTrue(d.size()==1);

  }

  @Test public void put() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    ArrayList<String> key = new ArrayList<String>();
    //add in the obj
    d.put("BOB",50000);
    key.add("BOB");
    //see if the lists are the same
    assertEquals((List) key,d.keys());
    d.pop("BOB");
    //pop bob and see if they still are the same
    key.clear();
    assertEquals((List) key,d.keys());

  }
  @Test public void size() {
    Dict<String,Integer> d = new Dict<String,Integer>();
    //start off with nothing
    assertTrue(d.size()==0);
    //add bob
    d.put("BOB",50000);
    assertTrue(d.size()==1);
    //add joe
    d.put("JOE",2017);
    assertTrue(d.size()==2);
    //remove bob
    d.pop("BOB");
    assertTrue(d.size()==1);
  }

  @Test public void tooString() {
    //TOOOOOO STWING
    Dict<String,Integer> d = new Dict<String,Integer>();
    assertEquals("{}",d.toString());
  }




}