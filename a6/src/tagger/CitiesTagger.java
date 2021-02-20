package tagger;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CitiesTagger {

    private Map<String, String> cities;
    private Map<String, Integer> taggedCities;
    //private long taggedCitiesCount; // -> taggedCities.size(); does the same job

    public CitiesTagger(Reader cities) throws IOException {
        this.cities = new HashMap<>();
        this.taggedCities = new HashMap<>();
        populateMapOfCities(cities);

    }

    /**
     * Processes an input stream of a text file, tags any cities and outputs result
     * to a text output stream.
     *
     * @param text   a java.io.Reader input stream containing text to be processed
     * @param output a java.io.Writer output stream containing the result of tagging
     */
    public void tagCities(Reader text, Writer output) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(text);
        BufferedWriter bufferedWriter = new BufferedWriter(output);
        String replacedWithTags = "";

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            String lineHolder = line;
            String[] tokens = line.trim().split(" +");
            // Set of words starting with capital letter
            Set<String> capital = Arrays.stream(tokens)
                    .filter(s -> s.matches("[A-Z][a-z]+"))
                    .collect(Collectors.toSet());
            for (String capitalWord : capital) {
                if (cities.containsKey(capitalWord)) {
                    if (taggedCities.containsKey(capitalWord)) {
                        taggedCities.put(capitalWord, (taggedCities.get(capitalWord)) + 1);
                    } else {
                        taggedCities.put(capitalWord, 1);
                    }

                    String replacement = String.format("<city country=\"%s\">%s</city>", cities.get(capitalWord), capitalWord);
                    if (lineHolder.contains(capitalWord)) {
                        replacedWithTags = lineHolder.replaceAll(capitalWord, replacement);
                    }


                }

            }

            if (replacedWithTags.isEmpty()) {
                replacedWithTags = lineHolder;
            }
            bufferedWriter.write(replacedWithTags);
            bufferedWriter.newLine();
            bufferedWriter.flush();


        }
        bufferedReader.close();
        bufferedWriter.close();

    }

    /**
     * Returns a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If @n exceeds the total number of cities tagged, return as many as available
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @param n the maximum number of top tagged cities to return
     * @return a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation.
     */
    public Collection<String> getNMostTaggedCities(int n) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(taggedCities.entrySet());
        sortedEntries.sort(new MapComparator());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(sortedEntries.get(i).getKey());
        }

        return result;
    }

    /**
     * Returns a collection of all tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @return a collection of all tagged cities' unique names
     * from the last tagCities() invocation.
     */
    public Collection<String> getAllTaggedCities() {
        return taggedCities.keySet();
    }

    /**
     * Returns the total number of tagged cities in the input text
     * from the last tagCities() invocation
     * In case a particular city has been taged in several occurences, all must be counted.
     * If tagCities() has not been invoked at all, return 0.
     *
     * @return the total number of tagged cities in the input text
     */
    public long getAllTagsCount() {
        int total = 0;
        for (int number : taggedCities.values()) {
            total += number;

        }
        return total;
    }


    private void populateMapOfCities(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String line;

        while ((line = br.readLine()) != null) {
            String[] tokens = line.trim().split(",");
            cities.put(tokens[0], tokens[1]);
        }
        br.close();

    }


    private class MapComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            Integer firstValue = taggedCities.get(o1.getKey());
            firstValue = firstValue != null ? firstValue : 0;

            Integer secondValue = taggedCities.get(o2.getKey());
            secondValue = secondValue != null ? secondValue : 0;

            return Integer.compare(secondValue, firstValue);
        }
    }
}

