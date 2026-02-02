package edu.eci.arsw.blacklistvalidator;

import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 *
 * @author Julian Lopez Barrero
 */

public class BlackListSearchThread extends Thread {
    private HostBlacklistsDataSourceFacade blacklist;
    private String ip;
    private int start, end, occurrencesCount;
    private List<Integer> occurrences = new ArrayList<>();

    public BlackListSearchThread(HostBlacklistsDataSourceFacade blacklist, String ip, int start, int end) {
        this.blacklist = blacklist;
        this.ip = ip;
        this.start = start;
        this.end = end;
        this.occurrencesCount = 0;
    }
    
    @Override
    public void run() {
        for(int i = start; i < end; i++) {
            if(blacklist.isInBlackListServer(i, ip)) {
                occurrences.add(i);
                occurrencesCount++;
            }
        }
        
    }

    public int getOccurrencesCount() {
        return occurrencesCount;
    }

    public List<Integer> getOccurrences() {
        return occurrences;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
    
}
