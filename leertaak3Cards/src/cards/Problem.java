package cards;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Problem
{
    private LinkedList<Candidate> candidates = new LinkedList<Candidate>() {
        {
            super.add(new Candidate('A',2)); //Ace
            super.add(new Candidate('K',2)); //King
            super.add(new Candidate('Q',2)); //Queen
            super.add(new Candidate('J',2)); //Jack
        }

        public Candidate remove(int index) //overwrites super.remove
        {
            Candidate candidate=get(index);
            candidate.takeOne();
            //System.out.println("remove index "+index +" "+ candidate);
            if (candidate.getAvailable()==0)
             {
                 candidate=super.remove(index);
             }
             return candidate;
        }

        public void add (int index, Candidate candidate) //overwrites super.add
        {
              candidate.addOne();
              //System.out.println("add index "+ index + " "+ candidate);
              if (candidate.getAvailable()==1)
              {
                  super.add(index,candidate);
              }
        }

        public String toString()
        {
            Iterator it=iterator();
            String rS="";
            while (it.hasNext())
            {
                rS+=it.next()+" ";
            }
            return rS;
        }
    };
    private Solution   solution   = new Solution();
    private Scanner    reader = new Scanner(System.in);
    
    public void solve()
    {
        //System.out.println(candidates);
        //System.out.println(solution);
        //reader.nextLine();
        int index=0;
        while (index<candidates.size())
        {
            if (solution.fits(candidates.get(index)))
            {
                solution.record(candidates.remove(index)); //move candidate to solution
                if (solution.complete())
                {
                    solution.show();
                }
                else
                {
                    solve();
                }
                candidates.add(index, solution.eraseRecording()); //move candidate to candidates
            }
            index++;
        }
    }
}
        
          
         









