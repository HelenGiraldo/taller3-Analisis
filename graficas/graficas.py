"""Gráficas globales del Punto 2 (sección 2.7): crecimiento en Java, crecimiento en Python
y comparación Java frente a Python con n = 1.000.000.

Los datos son los promedios por búsqueda (ns) de las tablas del documento:
 - Lineal, Binaria y Ternaria: fila "Ascendente" de las tablas 2.1, 2.2 y 2.3, pasada de ms a ns.
 - Saltos, Interpolación y Exponencial: escenario "claves mixtas" (500 presentes + 500 ausentes).
"""
import os
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.ticker import FixedLocator, FuncFormatter, NullFormatter

SALIDA = os.path.join(os.path.dirname(os.path.abspath(__file__)), "graficas")
os.makedirs(SALIDA, exist_ok=True)

NS = [1000, 10000, 100000, 1000000]
ALG = ["Lineal", "Binaria", "Ternaria", "Saltos", "Interpolación", "Exponencial"]
COLOR = {"Lineal": "#2a78d6", "Binaria": "#eb6834", "Ternaria": "#1baf7a",
         "Saltos": "#eda100", "Interpolación": "#e87ba4", "Exponencial": "#008300"}
MARCA = {"Lineal": "o", "Binaria": "s", "Ternaria": "^", "Saltos": "D", "Interpolación": "v", "Exponencial": "P"}
AZUL_JAVA, NARANJA_PY = "#2a78d6", "#eb6834"
TINTA, TINTA2, REJILLA = "#0b0b0b", "#52514e", "#e3e2de"

# ---- Promedio por búsqueda (ns) ----
JAVA = {"Lineal": [9100.0, 42200.0, 45800.0, 334000.0],
        "Binaria": [16.0, 17.0, 22.0, 25.0],
        "Ternaria": [9.0, 19.0, 22.0, 18.0],
        "Saltos": [60.62, 95.54, 218.90, 773.22],
        "Interpolación": [53.32, 22.10, 22.64, 72.56],
        "Exponencial": [81.16, 92.06, 102.16, 143.68]}
PYTHON = {"Lineal": [30300.0, 316700.0, 3025100.0, 29545300.0],
          "Binaria": [1101.0, 1574.0, 1918.0, 2277.0],
          "Ternaria": [1150.0, 1633.0, 2117.0, 2375.0],
          "Saltos": [1509.54, 4788.28, 14544.06, 45507.44],
          "Interpolación": [578.58, 682.96, 766.62, 1093.20],
          "Exponencial": [950.06, 1474.72, 1853.18, 2738.94]}

NOTA = ("Lineal, Binaria y Ternaria: fila ascendente de las tablas 2.1 a 2.3. "
        "Saltos, Interpolación y Exponencial: 1.000 claves mixtas (500 + 500).")

plt.rcParams.update({"font.size": 11, "text.color": TINTA, "axes.labelcolor": TINTA,
                     "xtick.color": TINTA2, "ytick.color": TINTA2})


def miles_punto(v, _=None):
    return f"{int(v):,}".replace(",", ".")


def potencia(v, _=None):
    import math
    e = int(round(math.log10(v)))
    sup = str(e).translate(str.maketrans("-0123456789", "⁻⁰¹²³⁴⁵⁶⁷⁸⁹"))
    return f"10{sup}"


def etiqueta(v):
    if v >= 1000:
        return f"{v:,.0f}".replace(",", " ")
    return f"{v:.2f}"


def estilo(ax):
    for lado in ("top", "right"):
        ax.spines[lado].set_visible(False)
    ax.grid(True, axis="y", color=REJILLA, linewidth=0.8)
    ax.set_axisbelow(True)


def separar(valores, minimo=0.2):
    """Separa etiquetas en escala log para que no se solapen (devuelve y de cada etiqueta)."""
    import math
    orden = sorted(range(len(valores)), key=lambda i: valores[i])
    pos = {i: math.log10(valores[i]) for i in orden}
    prev = None
    for i in orden:
        if prev is not None and pos[i] - pos[prev] < minimo:
            pos[i] = pos[prev] + minimo
        prev = i
    return {i: 10 ** pos[i] for i in orden}


def crecimiento(datos, lenguaje, archivo, ymin, ymax):
    fig, ax = plt.subplots(figsize=(9.2, 6))
    for a in ALG:
        ax.plot(NS, datos[a], color=COLOR[a], marker=MARCA[a], markersize=7, linewidth=2,
                markeredgecolor="white", markeredgewidth=1.2, label=a)
    finales = [datos[a][3] for a in ALG]
    ys = separar(finales)
    for i, a in enumerate(ALG):
        ax.annotate(etiqueta(finales[i]), xy=(NS[3], finales[i]), xytext=(NS[3] * 1.35, ys[i]),
                    va="center", fontsize=9, color=TINTA,
                    arrowprops=dict(arrowstyle="-", color=REJILLA, lw=0.8))
    ax.set_xscale("log"); ax.set_yscale("log")
    ax.xaxis.set_major_locator(FixedLocator(NS))
    ax.xaxis.set_major_formatter(FuncFormatter(miles_punto))
    ax.xaxis.set_minor_formatter(NullFormatter())
    ax.yaxis.set_major_formatter(FuncFormatter(potencia))
    ax.set_xlim(NS[0] / 1.5, NS[3] * 4)
    ax.set_ylim(ymin, ymax)
    ax.set_xlabel("n (tamaño del arreglo)")
    ax.set_ylabel("Tiempo promedio por búsqueda (ns)")
    ax.set_title(f"Algoritmos de búsqueda en {lenguaje}: tiempo promedio por búsqueda", loc="left", fontsize=12.5)
    estilo(ax)
    ax.legend(loc="upper left", frameon=False, ncol=2, fontsize=10)
    fig.text(0.01, 0.005, NOTA, fontsize=8, color=TINTA2, ha="left", va="bottom")
    fig.tight_layout(rect=(0, 0.03, 1, 1))
    fig.savefig(os.path.join(SALIDA, archivo), dpi=200, bbox_inches="tight", facecolor="white")
    plt.close(fig)


def barras(archivo):
    fig, ax = plt.subplots(figsize=(11, 6))
    ancho = 0.38
    x = list(range(len(ALG)))
    vj = [JAVA[a][3] for a in ALG]
    vp = [PYTHON[a][3] for a in ALG]
    bj = ax.bar([i - ancho / 2 for i in x], vj, ancho, color=AZUL_JAVA, label="Java")
    bp = ax.bar([i + ancho / 2 for i in x], vp, ancho, color=NARANJA_PY, label="Python")
    for barras_, vals in ((bj, vj), (bp, vp)):
        for r, v in zip(barras_, vals):
            ax.text(r.get_x() + r.get_width() / 2, v * 1.12, etiqueta(v), ha="center", va="bottom",
                    fontsize=8.5, color=TINTA)
    ax.set_yscale("log")
    ax.set_ylim(5, 2e8)
    ax.yaxis.set_major_formatter(FuncFormatter(potencia))
    ax.set_xticks(x)
    ax.set_xticklabels(ALG)
    ax.set_ylabel("Tiempo promedio por búsqueda (ns)")
    ax.set_title("Algoritmos de búsqueda con n = 1.000.000: Java frente a Python", loc="left", fontsize=12.5)
    estilo(ax)
    ax.legend(loc="upper right", frameon=False)
    fig.text(0.01, 0.005, NOTA, fontsize=8, color=TINTA2, ha="left", va="bottom")
    fig.tight_layout(rect=(0, 0.03, 1, 1))
    fig.savefig(os.path.join(SALIDA, archivo), dpi=200, bbox_inches="tight", facecolor="white")
    plt.close(fig)


if __name__ == "__main__":
    crecimiento(JAVA, "Java", "busqueda_crecimiento_java.png", 3, 3e6)
    crecimiento(PYTHON, "Python", "busqueda_crecimiento_python.png", 300, 2e8)
    barras("busqueda_java_vs_python_1000000.png")
    print("Gráficas guardadas en", SALIDA)