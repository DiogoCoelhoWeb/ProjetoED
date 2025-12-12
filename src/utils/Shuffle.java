package utils;

import interfaces.ListADT;

import java.util.Iterator;


public class Shuffle<T> {

    /**
     * Shuffles the elements of a given list using the Fisher-Yates Shuffle algorithm.
     * This method randomizes the order of the elements in the list and returns the
     * shuffled elements in an array.
     *
     * @param list the list of elements to be shuffled
     * @return an array containing the shuffled elements of the list
     */
    public  T[] shuffle(ListADT<T> list) {
        int count = list.size();
        Iterator<T> it;

        T[] listArray = (T[]) (new Object[count]);
        it = list.iterator();
        for (int i = 0; i < count; i++) {
            listArray[i] = it.next();
        }

        //  Fisher-Yates Shuffle
        for (int i = listArray.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));

            T temp = listArray[index];
            listArray[index] = listArray[i];
            listArray[i] = temp;
        }
        return listArray;
    }
}
