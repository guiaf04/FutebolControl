package services;

import models.Clube;
import models.Campeonato;
import models.Partida;
import utils.JsonUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Serviço para comunicação com a API do servidor
 */
public class ApiService {
    private final String baseUrl;

    public ApiService() {
        this("http://localhost:8000");
    }

    public ApiService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // ==================== MÉTODOS HTTP BÁSICOS ====================

    private ApiResponse sendPost(String endpoint, String jsonBody) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            if (jsonBody != null && !jsonBody.isEmpty()) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            return processResponse(conn);
        } catch (Exception e) {
            return new ApiResponse(false, "Erro: " + e.getMessage(), "");
        }
    }

    private ApiResponse sendGet(String endpoint) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return processResponse(conn);
        } catch (Exception e) {
            return new ApiResponse(false, "Erro: " + e.getMessage(), "");
        }
    }

    private ApiResponse sendPut(String endpoint, String jsonBody) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            return processResponse(conn);
        } catch (Exception e) {
            return new ApiResponse(false, "Erro: " + e.getMessage(), "");
        }
    }

    private ApiResponse sendDelete(String endpoint) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            return processResponse(conn);
        } catch (Exception e) {
            return new ApiResponse(false, "Erro: " + e.getMessage(), "");
        }
    }

    private ApiResponse processResponse(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        boolean success = responseCode >= 200 && responseCode < 300;

        InputStream inputStream = success ? conn.getInputStream() : conn.getErrorStream();
        String response = "";
        
        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            response = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
        }

        conn.disconnect();
        return new ApiResponse(success, success ? "Sucesso" : "Erro (" + responseCode + ")", response);
    }

    // ==================== MÉTODOS DE CLUBES ====================

    public ApiResponse criarClube(Clube clube) {
        return sendPost("/clubes", clube.toJson());
    }

    public ApiResponse<List<Clube>> listarClubes() {
        ApiResponse response = sendGet("/clubes");
        if (response.isSuccess()) {
            List<Clube> clubes = new ArrayList<>();
            String data = (String) response.getData();
            List<String> clubeJsons = JsonUtils.extractJsonObjects(data);
            for (String clubeJson : clubeJsons) {
                clubes.add(Clube.fromJson(clubeJson));
            }
            return new ApiResponse<>(true, "Sucesso", clubes);
        }
        return new ApiResponse<>(false, response.getMessage(), new ArrayList<>());
    }

    public ApiResponse<Clube> obterEstatisticasClube(String sigla) {
        ApiResponse response = sendGet("/clubes/estatisticas?sigla=" + sigla);
        if (response.isSuccess()) {
            String data = (String) response.getData();
            Clube clube = Clube.fromJson(data);
            return new ApiResponse<>(true, "Sucesso", clube);
        }
        return new ApiResponse<>(false, response.getMessage(), null);
    }

    public ApiResponse atualizarClube(String siglaOriginal, Clube clube) {
        try {
            String endpoint = "/clubes?sigla_original=" + URLEncoder.encode(siglaOriginal, "UTF-8");
            return sendPut(endpoint, clube.toJson());
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse(false, "Erro de codificação: " + e.getMessage(), "");
        }
    }

    public ApiResponse deletarClube(String sigla) {
        try {
            String endpoint = "/clubes?sigla=" + URLEncoder.encode(sigla, "UTF-8");
            return sendDelete(endpoint);
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse(false, "Erro de codificação: " + e.getMessage(), "");
        }
    }

    public ApiResponse<List<Partida>> obterHistoricoClube(String sigla) {
        try {
            String endpoint = "/clubes/partidas?sigla=" + URLEncoder.encode(sigla, "UTF-8");
            ApiResponse response = sendGet(endpoint);
            if (response.isSuccess()) {
                List<Partida> partidas = new ArrayList<>();
                String data = (String) response.getData();
                List<String> partidaJsons = JsonUtils.extractJsonObjects(data);
                for (String partidaJson : partidaJsons) {
                    partidas.add(Partida.fromJson(partidaJson));
                }
                return new ApiResponse<>(true, "Sucesso", partidas);
            }
            return new ApiResponse<>(false, response.getMessage(), new ArrayList<>());
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse<>(false, "Erro de codificação: " + e.getMessage(), new ArrayList<>());
        }
    }

    // ==================== MÉTODOS DE CAMPEONATOS ====================

    public ApiResponse criarCampeonato(Campeonato campeonato) {
        return sendPost("/campeonatos", campeonato.toJson());
    }

    public ApiResponse<List<Campeonato>> listarCampeonatos() {
        ApiResponse response = sendGet("/campeonatos");
        if (response.isSuccess()) {
            List<Campeonato> campeonatos = new ArrayList<>();
            String data = (String) response.getData();
            List<String> campeonatoJsons = JsonUtils.extractJsonObjects(data);
            for (String campeonatoJson : campeonatoJsons) {
                campeonatos.add(Campeonato.fromJson(campeonatoJson));
            }
            return new ApiResponse<>(true, "Sucesso", campeonatos);
        }
        return new ApiResponse<>(false, response.getMessage(), new ArrayList<>());
    }

    public ApiResponse atualizarCampeonato(String nomeOriginal, Campeonato campeonato) {
        try {
            String endpoint = "/campeonatos?nome_original=" + URLEncoder.encode(nomeOriginal, "UTF-8");
            return sendPut(endpoint, campeonato.toJson());
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse(false, "Erro de codificação: " + e.getMessage(), "");
        }
    }

    public ApiResponse deletarCampeonato(String nome) {
        try {
            String endpoint = "/campeonatos?nome=" + URLEncoder.encode(nome, "UTF-8");
            return sendDelete(endpoint);
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse(false, "Erro de codificação: " + e.getMessage(), "");
        }
    }

    public ApiResponse adicionarClubeAoCampeonato(String nomeCampeonato, String siglaClube) {
        try {
            String endpoint = "/campeonatos/adicionar_clube?nome_campeonato=" + 
                            URLEncoder.encode(nomeCampeonato, "UTF-8") + 
                            "&sigla_clube=" + URLEncoder.encode(siglaClube, "UTF-8");
            return sendPost(endpoint, "");
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse(false, "Erro de codificação: " + e.getMessage(), "");
        }
    }

    public ApiResponse<List<Clube>> listarClubesCampeonato(String nome) {
        try {
            String endpoint = "/campeonatos/clubes_participantes?nome=" + URLEncoder.encode(nome, "UTF-8");
            ApiResponse response = sendGet(endpoint);
            if (response.isSuccess()) {
                List<Clube> clubes = new ArrayList<>();
                String data = (String) response.getData();
                List<String> clubeJsons = JsonUtils.extractJsonObjects(data);
                for (String clubeJson : clubeJsons) {
                    clubes.add(Clube.fromJson(clubeJson));
                }
                return new ApiResponse<>(true, "Sucesso", clubes);
            }
            return new ApiResponse<>(false, response.getMessage(), new ArrayList<>());
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse<>(false, "Erro de codificação: " + e.getMessage(), new ArrayList<>());
        }
    }

    // ==================== MÉTODOS DE PARTIDAS ====================

    public ApiResponse registrarPartida(Partida partida) {
        return sendPost("/partidas", partida.toJson());
    }

    // ==================== CLASSE INTERNA PARA RESPOSTA ====================

    public static class ApiResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public T getData() { return data; }

        @Override
        public String toString() {
            return String.format("ApiResponse{success=%s, message='%s', data=%s}", 
                               success, message, data);
        }
    }
}
