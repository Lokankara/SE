package com.pp.app.array.comparator;

import com.pp.app.array.entity.string.StringArrayEntity;

import java.util.Comparator;

public enum StringArrayComparator implements Comparator<StringArrayEntity> {
    NAME {
        @Override
        public int compare(StringArrayEntity e1, StringArrayEntity e2) {
            return e1.getName().compareTo(e2.getName());
        }
    },
    ID {
        @Override
        public int compare(StringArrayEntity e1, StringArrayEntity e2) {
            return Integer.compare(e1.getId(), e2.getId());
        }
    },
    LENGTH_ASC {
        @Override
        public int compare(StringArrayEntity a1, StringArrayEntity a2) {
            return Integer.compare(a1.getArray().length, a2.getArray().length);
        }
    },
    LENGTH_DESC {
        @Override
        public int compare(StringArrayEntity a1, StringArrayEntity a2) {
            return Integer.compare(a2.getArray().length, a1.getArray().length);
        }
    },
    FIRST {
        @Override
        public int compare(StringArrayEntity a1, StringArrayEntity a2) {
            String[] arr1 = a1.getArray();
            String[] arr2 = a2.getArray();
            if (arr1.length == 0 && arr2.length == 0) return 0;
            if (arr1.length == 0) return -1;
            if (arr2.length == 0) return 1;
            return arr1[0].compareTo(arr2[0]);
        }
    },
    CONCATENATED {
        @Override
        public int compare(StringArrayEntity a1, StringArrayEntity a2) {
            String s1 = String.join("", a1.getArray());
            String s2 = String.join("", a2.getArray());
            return s1.compareTo(s2);
        }
    },

}