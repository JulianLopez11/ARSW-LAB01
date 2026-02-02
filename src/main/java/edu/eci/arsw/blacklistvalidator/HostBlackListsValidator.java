/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @param N number of threads to be used.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int N){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        LinkedList<BlackListSearchThread> threads = new LinkedList<>();
        
        int ocurrencesCount=0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int totalServers = skds.getRegisteredServersCount();
        int listsPerThread = totalServers / N;
        int remainingLists = totalServers % N;
        int start = 0;

        //Inicializar Hilos?
        for(int i = 0; i < N; i++) {

            int end = start + listsPerThread ;
            if(i == N - 1) {
                end += remainingLists; 
            }

            BlackListSearchThread thread = new BlackListSearchThread(skds, ipaddress, start, end);
            threads.add(thread);
            thread.start();
            start = end;
        }

        int checkedListsCount=0;

        //Uso del join
        for(BlackListSearchThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.log(Level.SEVERE, "Thread interrupted: {0}", e.getMessage());
            }
            ocurrencesCount += thread.getOccurrencesCount();
            blackListOcurrences.addAll(thread.getOccurrences());
            checkedListsCount += (thread.getEnd()-thread.getStart());
        }
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
