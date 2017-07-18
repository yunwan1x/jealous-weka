package weka.core.tokenizers;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import weka.core.logging.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by changhui.wy on 17/7/17.
 */
public class HanlpTokenizer extends Tokenizer  {



    private transient Iterator<Term> hanlpTk;

    @Override
    public boolean hasMoreElements() {
        return hanlpTk.hasNext();
    }

    @Override
    public String getRevision() {
        return "1.0";
    }

    @Override
    public String nextElement() {
        return hanlpTk.next().word;
    }

    @Override
    public String globalInfo() {
        return "自定义tokenizer";
    }

    @Override
    public void tokenize(String s) {
        hanlpTk = filter(s);
    }

    private Iterator<Term> filter(String s) {

        List<Term> hans = HanLP.segment(s);
        List<Term> rets=new ArrayList<>();
        for(Term t:hans)
        {
            boolean a = t.nature.toString().startsWith("n");
            boolean b = t.nature.toString().startsWith("v");
            boolean d=!t.nature.equals(Nature.nx);
            boolean e=!t.nature.equals(Nature.ns);
            boolean c = t.length() >= 2;
            boolean result=(a || b) &&d&&e;
            if(result){
                rets.add(t);
            }
        }
        return rets.iterator();
    }

    public static void main(String[] args) {
        runTokenizer(new HanlpTokenizer(), args);
    }
}
