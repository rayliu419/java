package tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupJson {

    /**
     * 如果有重复的，在组里会被去重。
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "input.txt";  // 输入文件路径
        int groupSize = 30;  // 每组30个数字
        List<Map<String, String>> groupedResults = new ArrayList<>();
        Map<String, String> currentGroup = new HashMap<>();

        ClassLoader classLoader = GroupJson.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                // 将数字添加到当前组
                currentGroup.put(line, "NORMAL");
                count++;

                // 如果达到组大小限制，则添加当前组到结果列表，并重置当前组
                if (count == groupSize) {
                    groupedResults.add(new HashMap<>(currentGroup));
                    currentGroup.clear();
                    count = 0;
                }
            }

            // 如果还有剩余的数字，添加到最后一个组
            if (!currentGroup.isEmpty()) {
                groupedResults.add(currentGroup);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输出每组的JSON
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < groupedResults.size(); i++) {
            try {
                String jsonResult = objectMapper.writeValueAsString(groupedResults.get(i));
                System.out.println("Group " + (i + 1) + ":\n" + jsonResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
