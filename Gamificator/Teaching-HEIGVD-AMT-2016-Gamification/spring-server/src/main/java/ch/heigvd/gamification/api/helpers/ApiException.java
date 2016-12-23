/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api.helpers;

/**
 *
 * @author Thibaut-PC
 */
public class ApiException extends Exception {
    private int code;
	public ApiException (int code, String msg) {
		super(msg);
		this.code = code;
	}

}
