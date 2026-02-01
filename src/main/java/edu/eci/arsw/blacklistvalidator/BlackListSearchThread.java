package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class BlackListSearchThread extends Thread {
    private HostBlacklistsDataSourceFacade blacklist;
    private String ip;
    private int start;
    private int end;
    private int ocurrencesCount;
    private List<Integer> occurrences = new LinkedList<>();

    @Override
    public void run() {
        for(int i = start; i < end; i++) {
            if(blacklist.isInBlackListServer(i, ip)) {
                occurrences.add(i);
                ocurrencesCount++;
            }
        }
        
    }

    public int getOccurrencesCount() {
        return ocurrencesCount;
    }

    public List<Integer> getOccurrences() {
        return occurrences;
    }
    
}
