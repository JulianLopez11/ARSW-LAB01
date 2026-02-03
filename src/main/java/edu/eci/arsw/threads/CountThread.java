/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread {
    private int a;
    private int b;

    public CountThread(int a , int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void run(){
        try{
            for(int i = a ; i<=b ; i++){
                System.out.println(i);
                Thread.sleep(400);
            }
        }catch(Exception e){

        }

    }

    
}
