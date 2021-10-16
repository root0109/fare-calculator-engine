/**
 * 
 */
package com.zaprit.farecalculator.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author vaibhav.singh
 */
@Data
@RequiredArgsConstructor
public class Tuple<X, Y>
{
	public final X x;
	public final Y y;
}
