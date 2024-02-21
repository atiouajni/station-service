import pandas as pd

df=pd.read_csv('pdv.csv')


#FINDING MAX AND MIN
p=df['id'].max()
q=df['id'].min()

print(p)
print(q)
