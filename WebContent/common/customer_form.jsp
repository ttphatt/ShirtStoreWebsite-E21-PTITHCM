		<table cellpadding="10px">
			<tr>
				<td align="left">Email</td>
				<td align="left"><input type="email" id="email" name="email" size="40" value="${customer.email}"  minlength="5" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">First Name</td>
				<td align="left"><input type="text" id="firstname" name="firstname" size="40" value="${customer.firstname}"  minlength="2" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Last Name</td>
				<td align="left"><input type="text" id="lastname" name="lastname" size="40" value="${customer.lastname}"  minlength="2" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Password</td>
				<td align="left"><input type="password" id="password" name="password" size="40" value="${customer.password}"  minlength="6" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Confirm password</td>
				<td align="left"><input type="password" id="confirmPassword" name="confirmPassword" size="40" value="${customer.password}" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Phone number</td>
				<td align="left"><input type="text" id="phone" name="phone" size="20" value="${customer.phoneNumber}"  minlength="10" maxlength="20" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Address Line 1</td>
				<td align="left"><input type="text" id="address1" name="address1" size="70" value="${customer.addressLine1}"  minlength="5" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Address Line 2</td>
				<td align="left"><input type="text" id="address2" name="address2" size="70" value="${customer.addressLine2}"  minlength="5" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">City</td>
				<td align="left"><input type="text" id="city" name="city" size="20" value="${customer.city}"  minlength="1" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">State</td>
				<td align="left"><input type="text" id="state" name="state" size="20" value="${customer.state}"  minlength="1" maxlength="50" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Zip Code</td>
				<td align="left"><input type="number" id="zip" name="zip" size="20" value="${customer.zipcode}"  minlength="5" maxlength="10" class="form-control"/></td>
			</tr>
			
			<tr>
				<td align="left">Country</td>
				<td align="left">
					<select name="country" id="country" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split">
						<c:forEach items="${mapCountries}" var="country">
							<option value="${country.value}"<c:if test='${customer.country eq country.value}'>selected='selected'</c:if> >${country.key}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr><td>&nbsp;</td></tr>
				
			<tr>
				<td colspan="2" align="center">
					<button type="submit" class="btn btn-danger">Save</button>
					<button type="button" class="btn btn-danger" onclick="history.go(-1);">Cancel</button>
				</td>
			</tr>
		</table>