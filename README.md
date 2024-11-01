# Analisador HTML

Java 17 - Amazon Corretto.

**Aluno**: Lemuel Kauê Manske de Liz

## Pacotes

- [html-tokenizer-view](./html-tokenizer-view): Pacote que contém código relacionado a interface gráfica do aplicativo, não possui lógica do analisador
- [html-tokenizer-parser](./html-tokenizer-parser): Pacote que contém código relacionado a lógica do analisador de tokens HTML, aqui as estruturas de dados em [data-structures](./data-structures) são utilizadas
- [data-structures](./data-structures): Pacote que contém código das estruturas de dados utilizadas no projeto: Pilha lista, Lista estática e Lista encadeada e algoritmo de ordenação _quick-sort_, além de outras estruturas de dados implementadas durante a disciplina

## Diagrama de classes

Link do [diagrama de classes](./class-diagram.jpg)

Os pacotes estsão organizados no diagrama: `list`, `stack`, `sort` e `html-tokenizer-parser`.

## Execução

Para executar o aplicativo de visualização de tokens HTML, execute o seguinte comando:

```bash
java -jar Trabalho.jar
```

Lembrando que é necessário ter o Java 17 instalado na máquina.
