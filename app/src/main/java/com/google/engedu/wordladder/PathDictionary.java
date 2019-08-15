/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordladder;

import android.nfc.Tag;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static HashSet<String> words = new HashSet<>();
    private static Map<String,Vertex> vertexMap=new HashMap<>();

    private static final String TAG = "PathDictionary";
    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Log.i("Word ladder", "Loading dict");
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;
            }

            Vertex newNode= new Vertex(word);
            vertexMap.put(word,newNode);

            for(String s:words)
                if(neigboureChecker(s,word)){
                newNode.getNeigbours().add(s);
                vertexMap.get(s).getNeigbours().add(word);
                }
            words.add(word);
        }
    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private ArrayList<String> neighbours(String word) {
        return vertexMap.get(word).getNeigbours();

    }

    public String[] findPath(String start, String end) {
        Log.d("called","fp");
        ArrayDeque<ArrayList<String>> deque = new ArrayDeque<>();
        //HashSet<String> visited = new HashSet<>();
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(start);
        deque.addLast(pathList);
        while(!deque.isEmpty())
        {
            ArrayList<String> pathFinder = deque.pollFirst();
            if(pathFinder.size()>8) //avoiding going very deep
            {
                continue;
            }
            Log.d("Bug","Hello");
            Log.d("Intial PF------>",""+pathFinder.toString());
            if(pathFinder == null)
            {
                Log.d("Continue","null");
                continue;
            }
            String tempEnd = pathFinder.get(pathFinder.size()-1);
            Log.d(TAG, "TEMPEND----->"+tempEnd);
            Log.d(TAG, "word2node.get---->"+vertexMap.get(tempEnd).getName());
            ArrayList<String> neighbours = vertexMap.get(tempEnd).getNeigbours();
            Log.d("Neighbour---sze--->"," "+neighbours.size());
            Log.d("NS------->",""+neighbours.toString());
            for(int j=0;j<neighbours.size();j++)
            {

                String newWord = neighbours.get(j);
                ArrayList<String> tempPath = new ArrayList<>();
                for(int k=0;k<pathFinder.size();k++)
                {
                    tempPath.add(pathFinder.get(k));
                }
                Log.d("newWord----->",""+newWord);
                if(newWord.equals(end))
                {
                    tempPath.add(end);
                    Log.d("Path after add end->",""+tempPath.toString());
                    return tempPath.toArray(new String[tempPath.size()]);
                }
               /* if(visited.contains(newWord))
                {
                    continue;
                }*/
                tempPath.add(newWord);
                deque.addLast(tempPath);
                Log.d("newPath------->",""+tempPath.toString());
                Log.d("pathFindeer after----->",""+pathFinder.toString());
            }
//Log.d("PF",""+pathFinder.toString());
        }
        return null;
    }


    public boolean neigboureChecker(String word1,String word2) {
        if (word1.length() != word2.length())
            return false;

        else {//if length is equal
            int c = 0;
            for (int i = 0; i < word1.length(); i++) {
                if (word1.charAt(i) != word2.charAt(i)) {

                    c++;
                }
            }
            if (c == 1)
                return true;
            else
                return false;
        }
    }
}
