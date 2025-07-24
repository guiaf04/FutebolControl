from pydantic import BaseModel


class Partida(BaseModel):
    sigla_clube1: str
    sigla_clube2: str
    gols1: int
    gols2: int
    campeonato: str
