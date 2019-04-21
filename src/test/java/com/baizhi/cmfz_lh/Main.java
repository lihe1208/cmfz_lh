
import java.io.*;

public class Main{//测试
    public static void main(String[] args) throws IOException {
        DataItem aDataItem;
        int aKey, size, n, keysPerCell;
        System.out.print("Enter size of hash table: ");
        size = getInt();
        System.out.print("Enter initial number of items: ");
        n = getInt();
        keysPerCell = 10;

        HashTable theHashTable = new HashTable(size);

        for(int j=0; j<n; j++){
            aKey = (int)(java.lang.Math.random() * keysPerCell * size);
            aDataItem = new DataItem(aKey);
            theHashTable.insert(aDataItem);
        }

        while(true){
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, delete, or find: ");
            char choice = getChar();
            switch(choice){
                case 's':
                    theHashTable.displayTable();
                    break;
                case 'i':
                    System.out.print("Enter key value to insert: ");
                    aKey = getInt();
                    aDataItem = new DataItem(aKey);
                    theHashTable.insert(aDataItem);
                    break;
                case 'd':
                    System.out.print("Enter key value to delete: ");
                    aKey = getInt();
                    theHashTable.delete(aKey);
                    break;
                case 'f':
                    System.out.print("Enter key value to find: ");
                    aKey = getInt();
                    aDataItem = theHashTable.find(aKey);
                    if(aDataItem != null)
                    {
                        System.out.println("Found " + aKey);
                    }
                    else
                        System.out.println("Could not find " + aKey);
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }
        }
    }

    public static String getString() throws IOException{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }

    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }




}
class DataItem { //数据
    private int iData;    // data item (key)

    public DataItem(int ii) {
        iData = ii;
    }
    public int getKey(){
        return iData;
    }

}

class HashTable{//数组实现的哈希表，开放地址法之线性探测
    private DataItem[] hashArray; //存放数据的数组
    private int arraySize;
    private DataItem nonItem; //用作删除标志

    public HashTable(int size) {//构造函数
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem(-1);   // deleted item key is -1
    }

    public void displayTable(){//显示哈希表
        System.out.print("Table: ");
        for(int j=0; j<arraySize; j++)
        {
            if(hashArray[j] != null)
                System.out.print(hashArray[j].getKey() + " ");
            else
                System.out.print("** ");
        }
        System.out.println("");
    }

    //哈希函数
    public int hashFunc(int key)
    {
        return key % arraySize;
    }


    //在哈希表中插入数据
    public void insert(DataItem item){
        int key = item.getKey();      // 获取数据的键值
        int hashVal = hashFunc(key);  // 计算其哈希值

        while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1){
            ++hashVal;                 // 插入位置被占，线性探测下一位置
            hashVal %= arraySize;   // 不让超过数组的大小
        }
        hashArray[hashVal] = item;  // 找到空位后插入
    }

    //在哈希表中删除
    public DataItem delete(int key) {
        int hashVal = hashFunc(key);  // 计算其哈希值

        while(hashArray[hashVal] != null){
            if(hashArray[hashVal].getKey() == key){
                DataItem temp = hashArray[hashVal]; // 记录已删除的数据
                hashArray[hashVal] = nonItem;       // 删除它
                return temp;
            }
            ++hashVal;  // 到下一单元找
            hashVal %= arraySize;
        }
        return null;    // 没有找到要删除的数据
    }

    //在哈希表中查找
    public DataItem find(int key) {
        int hashVal = hashFunc(key);  //哈希这个键

        while(hashArray[hashVal] != null) { // 直到空的单元
            if(hashArray[hashVal].getKey() == key)
                return hashArray[hashVal];   // 找到
            ++hashVal;                 // 去下一单元找
            hashVal %= arraySize;      // 不让超过数组的大小
        }
        return null;  // 没有找到
    }

}

