public class Palindrome {

    /**Given a string, return a Deque where the characters appear in the same order as in the string*/
    public Deque<Character> wordToDeque(String word){
        Deque<Character> d = new LinkedListDeque<>();
        if (word.length() == 0) {
            return d;
        }
        for (int i = 0; i< word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        return recurPalindrome(d);
    }

    public boolean recurPalindrome(Deque<Character> d) {
        if (d.isEmpty()) {return true;}
        if (d.size() == 1) {return true;}
        else if(d.removeFirst() == d.removeLast()) {
            return recurPalindrome(d);
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        return recurPalindrome(d, cc);
    }

    public boolean recurPalindrome(Deque<Character> d, CharacterComparator cc) {
        if (d.isEmpty()) {return true;}
        if (d.size() == 1) {return true;}
        else if(cc.equalChars(d.removeFirst(), d.removeLast())) {
            return recurPalindrome(d, cc);
        }
        return false;
    }
}