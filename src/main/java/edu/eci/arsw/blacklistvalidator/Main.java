/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String a[]){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        
        try (Scanner scan = new Scanner(System.in)) {
            int cores = Runtime.getRuntime().availableProcessors();
            int doubleCores = cores * 2;
            System.out.println("Available cores: " + cores);
            System.out.println("Double the number of cores: " + doubleCores);
            System.out.println("Host (IP): ");
            String ip = scan.nextLine();
            System.out.println("Numbers of threads: ");
            int threads = scan.nextInt();
            List<Integer> blackListOcurrences=hblv.checkHost(ip, threads);
            System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        }
        
    }
    
}