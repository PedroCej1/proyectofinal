from sqlalchemy import create_engine

def get_engine():
    return create_engine(
        "mysql+pymysql://root:root@mysql:3306/PRACTICA"
    )