package test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


class DataGenerator {
    private static final String[] CLASS_NAMES_PARTS = {
            "Integer",
            "Long",
            "Byte",
            "Short",
            "Character",
            "Double",
            "Float",
            "Boolean",
            "To",
            "List",
            "Array",
            "Linked",
            "Hash",
            "Map",
            "Set",
            "Tree",
            "Table",
            "Object",
            "Thread",
            "Data",
            "Generator",
            "Test",
            "Index",
            "Cashed",
            "Entry",
            "Impl",
            "My",
            "Super",
            "Searcher",
            "I",
            "Main",
            "Random",
            "String",
            "Model",
            "Abstract",
            "Action",
            "Annotation",
            "Value",
            "Visitor",
            "Border",
            "Button",
            "Cell",
            "Editor",
            "Collection",
            "Color",
            "Chooser",
            "Panel",
            "Document",
            "Context",
            "Attribute",
            "Element",
            "Service",
            "Executor",
            "Interruptible",
            "Channel",
            "Layout",
            "Node",
            "Dimensions",
            "Simple",
            "Immutable",
            "Method",
            "Error",
            "Synchronizer",
            "Preferences",
            "Processor",
            "Queue",
            "Region",
            "Painter",
            "Mode",
            "Script",
            "Engine",
            "Selectable",
            "Key",
            "Selector",
            "Writer",
            "Reader",
            "Buffered",
            "System",
            "Input",
            "Output",
            "Stream",
            "Exception",
            "Bundle",
            "Text",
            "Component"
    };

    /**
     * <p>Generates different kinds of unique Strings as class names.</p>
     * <br>
     * <p>If shouldBeAbsolutelyRandom and shouldBeLongAndSimilar parameters are both set to false -
     * then result would contain randomly formed strings from some real java classes parts.</p>
     * <p>These strings <u>could not be real names</u> of the classes, they just would be formed from parts of real classes names.</p>
     * <p>For example: ThreadToStreamSystem, ComponentISelectorImpl, QueueKeyScript, etc.</p>
     *
     * @param numberOfEntries how many strings should be returned
     * @param shouldBeAbsolutelyRandom if set to true then all strings would contains from random characters
     * @param shouldBeLongAndSimilar if set to true then strings would be like:
     *                               AAA, AAB, AAC, ... , AAZ, AAa, AAb, ... , AAz, ABA, ABB, ...
     * @param maxCharactersNumberInWord defines the length of each string
     * @return array of strings
     */
    static String[] generateNames(
            int numberOfEntries,
            boolean shouldBeAbsolutelyRandom,
            boolean shouldBeLongAndSimilar,
            byte maxCharactersNumberInWord) {

        System.out.println("Generating strings...");
        if (shouldBeAbsolutelyRandom) {
            return generateRandomNames(numberOfEntries, maxCharactersNumberInWord);
        }
        if (shouldBeLongAndSimilar) {
            return generateLongAndSimilarNames(numberOfEntries, maxCharactersNumberInWord);
        }

        return generateStringsFromVocabulary(numberOfEntries, maxCharactersNumberInWord);
    }

    static long[] generateDates(int numberOfEntries, boolean shouldBeAbsolutelyRandom) {
        System.out.println("Generating dates...");
        if (shouldBeAbsolutelyRandom) {
            return generateRandomDates(numberOfEntries);
        }

        long[] result = new long[numberOfEntries];
        long systemTime = System.currentTimeMillis();
        for (int i = 0; i < result.length; i++) {
            /*
            * Each timestamp consists of current time + some random value that depends of number of entries.
            * Math.sqrt() gives not so randomly data, because we need some repeats in dates for sorting algorithm.
            * */
            result[i] = systemTime + (long) (Math.random() * Math.sqrt(numberOfEntries));
        }

        return result;
    }

    static String getRandomClassNamePart() {
        return CLASS_NAMES_PARTS[(int) (Math.random() * CLASS_NAMES_PARTS.length)];
    }

    private static String[] generateRandomNames(int numberOfEntries, byte maxCharactersNumberInWord) {
        Set<String> strings = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        while (strings.size() < numberOfEntries) {
            sb.delete(0, sb.length());  // cleaning
            for (int i = 0; i < maxCharactersNumberInWord; i++) {
                sb.append((char) (97 + (int)(Math.random() * 26)));
            }
            strings.add(sb.toString());
        }
        return strings.toArray(new String[strings.size()]);
    }

    private static String[] generateLongAndSimilarNames(int numberOfEntries, byte maxCharactersNumberInWord) {
        StringBuilder startWith = new StringBuilder(maxCharactersNumberInWord);
        for (int i = 0; i < maxCharactersNumberInWord; i++) {
            startWith.append('A');
        }

        String[] result = new String[numberOfEntries];
        result[0] = startWith.toString();

        StringBuilder sb = new StringBuilder(maxCharactersNumberInWord);
        for (int i = 1; i < numberOfEntries; i++) {
            sb.delete(0, sb.length());
            sb.append(result[i - 1]);
            char currentCharacter;
            for (int j = sb.length() - 1; j >= 0 ; j--) {
                currentCharacter = sb.charAt(j);
                if (currentCharacter != 'z') {
                    if (currentCharacter == 'Z') {
                        sb.setCharAt(j, 'a');
                    } else {
                        sb.setCharAt(j, ++currentCharacter);
                    }
                    break;
                } else {
                    sb.setCharAt(j, 'A');
                }
            }
            result[i] = sb.toString();
        }
        return result;
    }

    private static String[] generateStringsFromVocabulary(int numberOfEntries, byte maxCharactersNumberInWord) {
        Set<String> result = new HashSet<>(numberOfEntries);
        StringBuilder sb = new StringBuilder(maxCharactersNumberInWord);

        while (result.size() < numberOfEntries) {
            sb.delete(0, sb.length());

            int numberOfPartsInWord = (int) (Math.random() * 3) + 1;    // from 1 to 3 parts in a word
            for (int i = 0; i < numberOfPartsInWord; i++) {
                sb.append(CLASS_NAMES_PARTS[(int) (Math.random() * CLASS_NAMES_PARTS.length)]);
            }

            if (sb.length() <= maxCharactersNumberInWord) {
                result.add(sb.toString());
            }
        }
        return result.toArray(new String[result.size()]);
    }

    private static long[] generateRandomDates(int numberOfEntries) {
        Random random = new Random();
        long[] result = new long[numberOfEntries];

        for (int i = 0; i < numberOfEntries; i++) {
            long l;

            if (i >= numberOfEntries - 20) {    // lets make last 20 entries the same
                l = Long.MAX_VALUE;
            } else {
                l = random.nextLong();

                // lets take only full length longs for easier comparison
                if (l < 999999999999999999L) {
                    i--;
                    continue;
                }

                // and only positive ones
                if (l < 0) l *= -1;
            }

            result[i] = l;
        }

        return result;
    }
}
