package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário simples para parsing básico de JSON
 * Nota: Esta é uma implementação simplificada para evitar dependências externas.
 * Em uma aplicação real, seria recomendado usar bibliotecas como Jackson ou Gson.
 */
public class JsonUtils {

    /**
     * Extrai um valor string de um campo JSON
     */
    public static String extractStringValue(String json, String fieldName) {
        String searchPattern = "\"" + fieldName + "\":";
        int fieldIndex = json.indexOf(searchPattern);
        if (fieldIndex == -1) return "";

        int valueStart = json.indexOf("\"", fieldIndex + searchPattern.length());
        if (valueStart == -1) return "";
        
        int valueEnd = json.indexOf("\"", valueStart + 1);
        if (valueEnd == -1) return "";

        return json.substring(valueStart + 1, valueEnd);
    }

    /**
     * Extrai um valor inteiro de um campo JSON
     */
    public static int extractIntValue(String json, String fieldName) {
        String searchPattern = "\"" + fieldName + "\":";
        int fieldIndex = json.indexOf(searchPattern);
        if (fieldIndex == -1) return 0;

        int valueStart = fieldIndex + searchPattern.length();
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd == -1) valueEnd = json.indexOf("}", valueStart);
        if (valueEnd == -1) return 0;

        String valueStr = json.substring(valueStart, valueEnd).trim();
        try {
            return Integer.parseInt(valueStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Extrai um array de strings de um campo JSON
     */
    public static List<String> extractStringArray(String json, String fieldName) {
        List<String> result = new ArrayList<>();
        String searchPattern = "\"" + fieldName + "\":";
        int fieldIndex = json.indexOf(searchPattern);
        if (fieldIndex == -1) return result;

        int arrayStart = json.indexOf("[", fieldIndex);
        int arrayEnd = json.indexOf("]", arrayStart);
        if (arrayStart == -1 || arrayEnd == -1) return result;

        String arrayContent = json.substring(arrayStart + 1, arrayEnd).trim();
        if (arrayContent.isEmpty()) return result;

        String[] items = arrayContent.split(",");
        for (String item : items) {
            String cleanItem = item.trim().replaceAll("\"", "");
            if (!cleanItem.isEmpty()) {
                result.add(cleanItem);
            }
        }

        return result;
    }

    /**
     * Extrai um objeto JSON de um array JSON
     */
    public static List<String> extractJsonObjects(String jsonArray) {
        List<String> objects = new ArrayList<>();
        
        if (!jsonArray.trim().startsWith("[") || !jsonArray.trim().endsWith("]")) {
            return objects;
        }

        String content = jsonArray.trim().substring(1, jsonArray.trim().length() - 1);
        if (content.trim().isEmpty()) return objects;

        int braceCount = 0;
        int start = 0;
        boolean inString = false;
        char prevChar = ' ';

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            
            if (c == '"' && prevChar != '\\') {
                inString = !inString;
            }
            
            if (!inString) {
                if (c == '{') {
                    if (braceCount == 0) start = i;
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        objects.add(content.substring(start, i + 1));
                    }
                }
            }
            
            prevChar = c;
        }

        return objects;
    }

    /**
     * Formata uma string para uso em JSON (escapa caracteres especiais)
     */
    public static String escapeJsonString(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    /**
     * Cria um array JSON a partir de uma lista de strings
     */
    public static String createJsonArray(List<String> items) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            sb.append("\"").append(escapeJsonString(items.get(i))).append("\"");
            if (i < items.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
