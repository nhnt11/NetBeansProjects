package userbincrash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    public static void main(String args[]) {
        long l1 = System.currentTimeMillis();
        try {
            File f = new File("/Users/Nihanth/Desktop/input.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            int targetWeight = Integer.parseInt(br.readLine());
            ArrayList<Item> items = new ArrayList();
            String buf;
            while ((buf = br.readLine()) != null) {
                items.add(new Item(buf));
            }
//            int targetWeight = 100000;
//            ArrayList<Item> items = new ArrayList();
//            for (int i = 0; i < 10000; i++) {
//                Item item = new Item();
//                items.add(item);
//                //System.out.println(item);
//            }
            int gcd = targetWeight;
            for (Item i : items) {
                gcd = gcd(gcd, i.getWeight());
            }
            targetWeight /= gcd;
            for (Item i : items) {
                i.setWeight(i.getWeight() / gcd);
            }
            System.out.println(getMinPrice(items, targetWeight));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    static int getMinPrice(ArrayList<Item> items, int targetWeight) {
        long t1 = System.currentTimeMillis();
        int minPrices[] = new int[targetWeight + 1];
        for (int i = 0; i < minPrices.length; i++) {
            int currMinPrice = 0;
            for (Item it : items) {
                int wDiff = i - (int)it.getWeight();
                int price = (int)it.getPrice() + (wDiff <= 0 ? 0 : minPrices[wDiff]);
                if (currMinPrice == 0) currMinPrice = price;
                currMinPrice = Math.min(currMinPrice, price);
            }
            minPrices[i] = currMinPrice;
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        return minPrices[targetWeight];
    }

    static int gcd(int a, int b) {
        if (b == 0) return a;
        else return gcd(b, a%b);
    }

}

class Item extends Object implements Cloneable {

    private String mSKU;
    private int mWeight;
    private int mPrice;

    Item(String SKU, int weight, int price) {
        mSKU = SKU;
        mWeight = weight;
        mPrice = price;
    }

    Item(String line) {
        StringTokenizer st = new StringTokenizer(line);
        //mSKU = st.nextToken();
        mWeight = Integer.parseInt(st.nextToken());
        mPrice = Integer.parseInt(st.nextToken());
    }

    Item(ArrayList<Item> items) {
        mSKU = "";
        mWeight = 0;
        mPrice = 0;
        for (Item item : items) {
            mSKU += item.getSKU();
            mWeight += item.getWeight();
            mPrice += item.getPrice();
        }
    }

    Item() {
        mSKU = "";
        for (int i = 0; i < 6; i++) {
            mSKU += (char) (Math.random() * 26 + 50);
        }
        mWeight = 100 + ((int) (Math.random() * 500) * 10);
        mPrice = 2000 + ((int) (Math.random() * 200) * 100);
    }

    @Override
    protected Item clone() {
        return new Item(mSKU, mWeight, mPrice);
    }

    String getSKU() {
        return mSKU;
    }

    int getWeight() {
        return mWeight;
    }

    int getPrice() {
        return mPrice;
    }

    void setWeight(int weight) {
        mWeight = weight;
    }

    public boolean equals(Item it) {
        return it.getSKU().equals(getSKU())
                && it.getWeight() == getWeight()
                && it.getPrice() == getPrice();
    }

    @Override
    public String toString() {
        return mSKU + " " + mWeight + " " + mPrice;
    }
}
