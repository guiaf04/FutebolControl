from pydantic import BaseModel


class Clube(BaseModel):
    nome: str
    sigla: str
    vitorias: int = 0
    empates: int = 0
    derrotas: int = 0
    gols_pro: int = 0
    gols_contra: int = 0
