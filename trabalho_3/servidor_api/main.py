from fastapi import FastAPI, HTTPException, Query
from fastapi.middleware.cors import CORSMiddleware
from typing import List, Dict, Optional
from models import Clube, Campeonato, Partida

app = FastAPI()

# Libera acesso externo para clientes JS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

clubes: Dict[str, Clube] = {}
campeonatos: Dict[str, Campeonato] = {}
partidas: List[Partida] = []

@app.post("/clubes")
def criar_clube(clube: Clube):
    if clube.sigla in clubes:
        raise HTTPException(status_code=400, detail="Clube já existe")
    clubes[clube.sigla] = clube
    return {"mensagem": f"Clube {clube.nome} criado com sucesso"}

@app.get("/clubes")
def listar_clubes():
    return list(clubes.values())

@app.put("/clubes")
def atualizar_clube(sigla_original: str = Query(..., description="Sigla do clube a ser atualizado"), dados: Clube = None):
    if sigla_original not in clubes:
        raise HTTPException(status_code=404, detail="Clube não encontrado")
    
    if dados is None:
        raise HTTPException(status_code=400, detail="Dados de atualização não fornecidos")

    # Se a sigla foi alterada, remove a entrada antiga e adiciona a nova
    if sigla_original != dados.sigla:
        temp_clube = clubes.pop(sigla_original)
        temp_clube.nome = dados.nome
        temp_clube.sigla = dados.sigla
        clubes[dados.sigla] = temp_clube
    else:
        clubes[sigla_original].nome = dados.nome
        clubes[sigla_original].sigla = dados.sigla # Mantém a sigla original se não alterada

    return {"mensagem": f"Clube {sigla_original} atualizado com sucesso para {dados.sigla}"}


@app.delete("/clubes")
def deletar_clube(sigla: str = Query(..., description="Sigla do clube a ser deletado")):
    if sigla not in clubes:
        raise HTTPException(status_code=404, detail="Clube não encontrado")
    del clubes[sigla]
    return {"mensagem": f"Clube {sigla} deletado com sucesso"}

@app.get("/clubes/estatisticas")
def ver_estatisticas_clube(sigla: str = Query(..., description="Sigla do clube para ver estatísticas")):
    clube = clubes.get(sigla)
    if not clube:
        raise HTTPException(status_code=404, detail="Clube não encontrado")
    return clube

@app.get("/clubes/partidas")
def historico_partidas_clube(sigla: str = Query(..., description="Sigla do clube para ver o histórico de partidas")):
    if sigla not in clubes:
        raise HTTPException(status_code=404, detail="Clube não encontrado")
    historico = [p for p in partidas if p.sigla_clube1 == sigla or p.sigla_clube2 == sigla]
    return historico

@app.post("/campeonatos")
def criar_campeonato(campeonato: Campeonato):
    if campeonato.nome in campeonatos:
        raise HTTPException(status_code=400, detail="Campeonato já existe")
    campeonatos[campeonato.nome] = campeonato
    return {"mensagem": f"Campeonato {campeonato.nome} criado"}

@app.get("/campeonatos")
def listar_campeonatos():
    return list(campeonatos.values())

@app.put("/campeonatos")
def atualizar_campeonato(
    nome_original: str = Query(..., description="Nome do campeonato a ser atualizado"),
    dados: Campeonato = None
):
    if nome_original not in campeonatos:
        raise HTTPException(status_code=404, detail="Campeonato não encontrado")

    if dados is None:
        raise HTTPException(status_code=400, detail="Dados de atualização não fornecidos")

    # Se o nome foi alterado, remove a entrada antiga e adiciona a nova
    if nome_original != dados.nome:
        temp_campeonato = campeonatos.pop(nome_original)
        temp_campeonato.nome = dados.nome
        temp_campeonato.ano = dados.ano
        # Clubes serão os da requisição, assumindo que são os clubes atuais
        temp_campeonato.clubes = dados.clubes 
        campeonatos[dados.nome] = temp_campeonato
    else:
        campeonatos[nome_original].nome = dados.nome
        campeonatos[nome_original].ano = dados.ano
        campeonatos[nome_original].clubes = dados.clubes


    return {"mensagem": f"Campeonato {nome_original} atualizado com sucesso para {dados.nome}"}


@app.delete("/campeonatos")
def deletar_campeonato(nome: str = Query(..., description="Nome do campeonato a ser deletado")):
    if nome not in campeonatos:
        raise HTTPException(status_code=404, detail="Campeonato não encontrado")
    del campeonatos[nome]
    return {"mensagem": f"Campeonato {nome} deletado com sucesso"}

@app.post("/campeonatos/adicionar_clube")
def adicionar_clube_ao_campeonato(
    nome_campeonato: str = Query(..., description="Nome do campeonato"),
    sigla_clube: str = Query(..., description="Sigla do clube a ser adicionado")
):
    campeonato = campeonatos.get(nome_campeonato)
    if not campeonato:
        raise HTTPException(status_code=404, detail="Campeonato não encontrado")
    if sigla_clube not in clubes:
        raise HTTPException(status_code=404, detail="Clube não encontrado")
    if sigla_clube in campeonato.clubes:
        raise HTTPException(status_code=400, detail="Clube já está no campeonato")
    campeonato.clubes.append(sigla_clube)
    return {"mensagem": f"Clube {sigla_clube} adicionado ao campeonato {nome_campeonato}"}

@app.get("/campeonatos/clubes_participantes")
def listar_clubes_campeonato(nome: str = Query(..., description="Nome do campeonato")):
    campeonato = campeonatos.get(nome)
    if not campeonato:
        raise HTTPException(status_code=404, detail="Campeonato não encontrado")
    return [clubes[s] for s in campeonato.clubes if s in clubes]

@app.post("/partidas")
def registrar_partida(p: Partida):
    if p.sigla_clube1 not in clubes or p.sigla_clube2 not in clubes:
        raise HTTPException(status_code=404, detail="Clube não encontrado")

    if p.campeonato not in campeonatos:
        raise HTTPException(status_code=404, detail="Campeonato não encontrado")

    c1 = clubes[p.sigla_clube1]
    c2 = clubes[p.sigla_clube2]

    # Atualiza estatísticas
    c1.gols_pro += p.gols1
    c1.gols_contra += p.gols2

    c2.gols_pro += p.gols2
    c2.gols_contra += p.gols1

    if p.gols1 > p.gols2:
        c1.vitorias += 1
        c2.derrotas += 1
    elif p.gols1 < p.gols2:
        c2.vitorias += 1
        c1.derrotas += 1
    else:
        c1.empates += 1
        c2.empates += 1

    partidas.append(p)
    return {"mensagem": "Partida registrada com sucesso"}


@app.get("/")
def root():
    return {"mensagem": "API de Futebol - Online"}

@app.on_event("startup")
def inicializar_dados():
    # === Clubes reais ===
    clubes["FOR"] = Clube(nome="Fortaleza EC", sigla="FOR")
    clubes["FER"] = Clube(nome="Ferroviária", sigla="FER")
    clubes["CEA"] = Clube(nome="Ceará SC", sigla="CEA")
    clubes["FLA"] = Clube(nome="Flamengo", sigla="FLA")
    clubes["FLU"] = Clube(nome="Fluminense", sigla="FLU")
    clubes["GRE"] = Clube(nome="Grêmio", sigla="GRE")
    clubes["BAH"] = Clube(nome="Bahia", sigla="BAH")

    # === Campeonatos ===
    campeonatos["Cearense2024"] = Campeonato(
        nome="Cearense2024", ano=2024, clubes=["FOR", "CEA", "FER"]
    )
    campeonatos["SerieA2024"] = Campeonato(
        nome="SerieA2024", ano=2024, clubes=["FLA", "FLU", "GRE", "BAH"]
    )

    # === Partidas ===
    partidas_iniciais = [
        Partida(sigla_clube1="FOR", sigla_clube2="CEA", gols1=2, gols2=0, campeonato="Cearense2024"),
        Partida(sigla_clube1="FER", sigla_clube2="FOR", gols1=1, gols2=3, campeonato="Cearense2024"),
        Partida(sigla_clube1="FLA", sigla_clube2="FLU", gols1=2, gols2=2, campeonato="SerieA2024"),
        Partida(sigla_clube1="GRE", sigla_clube2="BAH", gols1=1, gols2=0, campeonato="SerieA2024"),
    ]

    for p in partidas_iniciais:
        c1 = clubes[p.sigla_clube1]
        c2 = clubes[p.sigla_clube2]

        c1.gols_pro += p.gols1
        c1.gols_contra += p.gols2

        c2.gols_pro += p.gols2
        c2.gols_contra += p.gols1

        if p.gols1 > p.gols2:
            c1.vitorias += 1
            c2.derrotas += 1
        elif p.gols1 < p.gols2:
            c2.vitorias += 1
            c1.derrotas += 1
        else:
            c1.empates += 1
            c2.empates += 1
        partidas.append(p)

    print("Dados carregados com sucesso:")
    print(f"• Clubes: {', '.join(clubes.keys())}")
    print(f"• Campeonatos: {', '.join(campeonatos.keys())}")