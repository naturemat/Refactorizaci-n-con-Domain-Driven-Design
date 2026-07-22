# -*- coding: utf-8 -*-
# ============================================================================
# UNIVERSIDAD CENTRAL DEL ECUADOR
# Sistema de Registro de Novedades en Laboratorios de Computo  (v1.0)
# ----------------------------------------------------------------------------
# ADVERTENCIA: Este archivo esta escrito a proposito como codigo "espagueti".
# TODA la logica esta mezclada en un solo archivo: datos, reglas de negocio,
# validaciones, "persistencia", ruteo de administradores, notificaciones e
# impresion por pantalla. NO sigue ninguna arquitectura ni patron.
# Tu tarea (ver enunciado) es entender el negocio y re-disenarlo con DDD.
# ============================================================================

import datetime
import random

# ------------------------ "BASE DE DATOS" GLOBAL ----------------------------
# Todo vive en variables globales. No hay capas ni modelos.

usuarios = [
    {"id": 1, "nombre": "Ana Torres",   "rol": "estudiante", "cedula": "1712345678", "carrera": "Sistemas"},
    {"id": 2, "nombre": "Luis Perez",   "rol": "estudiante", "cedula": "1798765432", "carrera": "Software"},
    {"id": 3, "nombre": "Marta Ruiz",   "rol": "admin",      "cedula": "1700000001", "area": "hardware"},
    {"id": 4, "nombre": "Jorge Vaca",   "rol": "admin",      "cedula": "1700000002", "area": "software"},
    {"id": 5, "nombre": "Sofia Leon",   "rol": "admin",      "cedula": "1700000003", "area": "redes"},
]

# labs: cada lab tiene un edificio, un piso y una lista de maquinas (numero de PC)
laboratorios = [
    {"codigo": "LAB-FING-01", "nombre": "Lab Ingenieria 1", "edificio": "FING", "piso": 2, "maquinas": list(range(1, 21)), "activo": True},
    {"codigo": "LAB-FING-02", "nombre": "Lab Ingenieria 2", "edificio": "FING", "piso": 3, "maquinas": list(range(1, 31)), "activo": True},
    {"codigo": "LAB-ADMIN-01","nombre": "Lab Administracion","edificio": "ADM", "piso": 1, "maquinas": list(range(1, 16)), "activo": False},
]

# Aqui se guardan las novedades registradas
novedades = []
secuencia_novedad = 0

# Historial de "correos" enviados a administradores (notificaciones)
bandeja_correos = []


# ------------------------ FUNCIONES MEZCLADAS -------------------------------

def registrar_novedad(id_usuario, codigo_lab, numero_maquina, tipo_incidente, descripcion):
    # Esta funcion hace de TODO: valida, aplica reglas, decide admin, guarda y notifica.
    global secuencia_novedad

    # 1) buscar usuario (a mano)
    u = None
    for x in usuarios:
        if x["id"] == id_usuario:
            u = x
    if u is None:
        print("ERROR: usuario no existe")
        return None
    if u["rol"] != "estudiante":
        print("ERROR: solo un estudiante puede registrar una novedad")
        return None

    # 2) buscar laboratorio (a mano) y validar
    lab = None
    for l in laboratorios:
        if l["codigo"] == codigo_lab:
            lab = l
    if lab is None:
        print("ERROR: laboratorio no existe")
        return None
    if lab["activo"] == False:
        print("ERROR: el laboratorio " + codigo_lab + " esta inactivo, no se aceptan novedades")
        return None

    # 3) validar que la maquina exista dentro del lab
    if numero_maquina not in lab["maquinas"]:
        print("ERROR: la maquina " + str(numero_maquina) + " no pertenece al lab " + codigo_lab)
        return None

    # 4) validar tipo de incidente (magic strings por todos lados)
    tipos_validos = ["hardware", "software", "red", "perifericos", "otro"]
    if tipo_incidente not in tipos_validos:
        print("ERROR: tipo de incidente invalido. Use: " + str(tipos_validos))
        return None

    # 5) validar descripcion
    if descripcion is None or len(descripcion.strip()) < 10:
        print("ERROR: la descripcion debe tener al menos 10 caracteres")
        return None

    # 6) REGLA DE NEGOCIO: no permitir 2 novedades ABIERTAS de la misma maquina
    for n in novedades:
        if n["lab"] == codigo_lab and n["maquina"] == numero_maquina and n["estado"] == "ABIERTA":
            print("AVISO: ya existe una novedad abierta para esa maquina (#" + str(n["id"]) + ")")
            return None

    # 7) RUTEO: decidir a que administrador le llega segun el tipo
    #    (esto deberia ser una regla de dominio clara, aqui esta como if/elif suelto)
    area_destino = ""
    if tipo_incidente == "hardware":
        area_destino = "hardware"
    elif tipo_incidente == "perifericos":
        area_destino = "hardware"   # perifericos tambien lo ve hardware
    elif tipo_incidente == "software":
        area_destino = "software"
    elif tipo_incidente == "red":
        area_destino = "redes"
    else:
        area_destino = "software"   # "otro" cae por defecto en software

    admin_asignado = None
    for a in usuarios:
        if a["rol"] == "admin" and a.get("area") == area_destino:
            admin_asignado = a
    if admin_asignado is None:
        print("ERROR: no hay administrador para el area " + area_destino)
        return None

    # 8) PRIORIDAD: otra regla escondida. Red y hardware son mas urgentes.
    if tipo_incidente == "red" or tipo_incidente == "hardware":
        prioridad = "ALTA"
    elif tipo_incidente == "software":
        prioridad = "MEDIA"
    else:
        prioridad = "BAJA"

    # 9) "persistir"
    secuencia_novedad = secuencia_novedad + 1
    codigo_ticket = "NOV-" + str(datetime.datetime.now().year) + "-" + str(secuencia_novedad).zfill(4)
    registro = {
        "id": secuencia_novedad,
        "ticket": codigo_ticket,
        "usuario": id_usuario,
        "usuario_nombre": u["nombre"],
        "lab": codigo_lab,
        "maquina": numero_maquina,
        "tipo": tipo_incidente,
        "descripcion": descripcion.strip(),
        "estado": "ABIERTA",
        "prioridad": prioridad,
        "admin": admin_asignado["id"],
        "admin_nombre": admin_asignado["nombre"],
        "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
    }
    novedades.append(registro)

    # 10) NOTIFICAR al admin (otra responsabilidad mas dentro de la misma funcion)
    correo = {
        "para": admin_asignado["nombre"],
        "asunto": "[" + prioridad + "] Nueva novedad " + codigo_ticket + " en " + codigo_lab,
        "cuerpo": "El estudiante " + u["nombre"] + " reporto un incidente de tipo '" + tipo_incidente +
                  "' en la maquina " + str(numero_maquina) + ". Detalle: " + descripcion.strip(),
    }
    bandeja_correos.append(correo)

    # 11) imprimir en pantalla (UI mezclada con logica)
    print(">> Novedad registrada: " + codigo_ticket)
    print("   Lab: " + codigo_lab + " | Maquina: " + str(numero_maquina) + " | Tipo: " + tipo_incidente)
    print("   Prioridad: " + prioridad + " | Asignado a: " + admin_asignado["nombre"] + " (" + area_destino + ")")
    return registro


def cerrar_novedad(id_novedad, id_admin, solucion):
    # Cerrar una novedad. Solo el admin asignado puede cerrarla.
    obj = None
    for n in novedades:
        if n["id"] == id_novedad:
            obj = n
    if obj is None:
        print("ERROR: novedad no existe")
        return
    if obj["estado"] == "CERRADA":
        print("ERROR: la novedad ya estaba cerrada")
        return
    if obj["admin"] != id_admin:
        print("ERROR: solo el administrador asignado puede cerrar esta novedad")
        return
    if solucion is None or len(solucion.strip()) < 5:
        print("ERROR: debe escribir la solucion aplicada")
        return
    obj["estado"] = "CERRADA"
    obj["solucion"] = solucion.strip()
    obj["fecha_cierre"] = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    print(">> Novedad " + obj["ticket"] + " cerrada por " + obj["admin_nombre"])


def listar_novedades_de_admin(id_admin):
    print("--- Bandeja del administrador " + str(id_admin) + " ---")
    hay = False
    for n in novedades:
        if n["admin"] == id_admin:
            hay = True
            print("  " + n["ticket"] + " | " + n["estado"] + " | " + n["prioridad"] +
                  " | " + n["lab"] + " maq " + str(n["maquina"]) + " | " + n["tipo"])
    if not hay:
        print("  (sin novedades)")


def reporte_general():
    print("========== REPORTE GENERAL ==========")
    total = len(novedades)
    abiertas = 0
    cerradas = 0
    for n in novedades:
        if n["estado"] == "ABIERTA":
            abiertas += 1
        else:
            cerradas += 1
    print("Total: " + str(total) + " | Abiertas: " + str(abiertas) + " | Cerradas: " + str(cerradas))
    # conteo por tipo (a mano)
    conteo = {}
    for n in novedades:
        conteo[n["tipo"]] = conteo.get(n["tipo"], 0) + 1
    print("Por tipo: " + str(conteo))
    print("=====================================")


def ver_bandeja_correos():
    print("########## CORREOS ENVIADOS ##########")
    for c in bandeja_correos:
        print("Para: " + c["para"])
        print("Asunto: " + c["asunto"])
        print("-" * 40)


# ------------------------ DEMO / EJECUCION ----------------------------------
# Al correr el archivo se ejecuta un escenario de ejemplo para ver el negocio.

def demo():
    print("### DEMO SISTEMA DE NOVEDADES - UNIVERSIDAD CENTRAL ###\n")

    # Ana (estudiante) reporta hardware en LAB-FING-01, maquina 5
    registrar_novedad(1, "LAB-FING-01", 5, "hardware", "El computador no enciende, no da video")
    print()

    # Luis reporta software en LAB-FING-02, maquina 12
    registrar_novedad(2, "LAB-FING-02", 12, "software", "No abre el Visual Studio Code, marca error")
    print()

    # Luis reporta red en LAB-FING-02, maquina 30
    registrar_novedad(2, "LAB-FING-02", 30, "red", "No hay internet en esta fila de maquinas")
    print()

    # Intento invalido: maquina que no existe en el lab
    registrar_novedad(1, "LAB-FING-01", 99, "hardware", "Pantalla azul constante")
    print()

    # Intento invalido: lab inactivo
    registrar_novedad(1, "LAB-ADMIN-01", 3, "software", "El office no tiene licencia activa")
    print()

    # Intento invalido: novedad duplicada en misma maquina abierta
    registrar_novedad(2, "LAB-FING-01", 5, "hardware", "Sigue sin encender el equipo numero cinco")
    print()

    # Un admin cierra una novedad que le corresponde
    cerrar_novedad(1, 3, "Se cambio la fuente de poder del equipo")
    print()

    # Un admin intenta cerrar una que NO le corresponde
    cerrar_novedad(2, 3, "intento indebido")
    print()

    listar_novedades_de_admin(3)
    listar_novedades_de_admin(4)
    print()
    reporte_general()
    print()
    ver_bandeja_correos()


if __name__ == "__main__":
    demo()
