def gcd(a, b):
    while b:
        a, b = b, a % b
    return a

def mod_inv(a, m):
    m0, x0, x1 = m, 0, 1
    if m == 1:
        return 0
    while a > 1:
        q = a // m
        m, a = a % m, m
        x0, x1 = x1 - q * x0, x0
    if x1 < 0:
        x1 += m0
    return x1
    
def encrypt(msg, pub):
    if len(msg) != len(pub): print("Invalid")
    
    CT = sum(pub[i] for i in range(len(msg)) if msg[i] == '1')
    return CT
    
def decrypt(CT, prv):
    w,m,r_inv = prv
    easy_sum = (CT*r_inv) % m
    plaintext = ""
    for i in range(len(w)-1, -1, -1):
        if easy_sum >= w[i]:
            plaintext = '1' + plaintext
            easy_sum -= w[i]
        else:
            plaintext = '0'+ plaintext
    
    return plaintext
    
w_ip = input("Enter superincreasing sequence: ")
w = list(map(int, w_ip.split(" ")))
m = int(input("Enter modulus m: "))
r = int(input("Enter multiplier r (coprime with m): "))

if m <= sum(w): print("Invalid")
if gcd(r,m) != 1: print("Invalid")

pub = [(wi * r) % m for wi in w]
r_inv = mod_inv(r,m)

print("\n--- Generated Keys ---")
print(f"\nFor Encryption: ")
print(f"Public Key: {pub}")
print(f"Private Key (w): {w}")
print(f"\nFor Decryption: ")
print(f"Private Key (m): {m}")
print(f"Private Key (r_inv): {r_inv}")
print("----------------------")

msg = input("Enter binary text: ")
CT = encrypt(msg, pub)
print(f"\nEncrypted Message, {CT}")
decrypt_msg = decrypt(CT, (w, m, r_inv))
print(f"Decrypted Message, {decrypt_msg}")

if msg==decrypt_msg:
    print("Success")
else:
    print("Failed")
