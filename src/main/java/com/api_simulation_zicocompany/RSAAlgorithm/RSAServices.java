package com.api_simulation_zicocompany.RSAAlgorithm;

import java.math.BigInteger;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

public interface RSAServices {
	int sign(int x,int privateKey, int n);
	boolean verify(int x, int signature, int publicKey, int n);
}
@Component
@Lazy
class RSAServiceImpl implements RSAServices{
	@Override
	public int sign(int x,int privateKey, int n) {
		BigInteger private_key_bignumber = BigInteger.valueOf(privateKey);
		BigInteger N_BigNumber = BigInteger.valueOf(n);
		BigInteger X_BigNumber = BigInteger.valueOf(x);
		BigInteger result = X_BigNumber.modPow(private_key_bignumber, N_BigNumber); 
		return result.intValue();
	}

	@Override
	public boolean verify(int x, int signature, int publicKey, int n) {
		BigInteger X_BigNumber = BigInteger.valueOf(x);
		BigInteger sign_BigNumber = BigInteger.valueOf(signature);
		BigInteger publicKey_BigNumber = BigInteger.valueOf(publicKey);
		BigInteger N_BigNumber = BigInteger.valueOf(n);
		
		if (X_BigNumber.pow(n).equals(sign_BigNumber.modPow(publicKey_BigNumber, N_BigNumber))) 
			return true;
		return false;
	}
}
