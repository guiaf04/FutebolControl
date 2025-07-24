from pydantic import BaseModel
from typing import List


class Campeonato(BaseModel):
    nome: str
    ano: int
    clubes: List[str] = []
