package com.ram.dto;

public class AccountDetailsFields
{
  private String accountType;
  private String accountDesc;
  private String username;
  private String password;
  
  public String getAccountType()
  {
    return this.accountType;
  }
  
  public void setAccountType(String accountType)
  {
    this.accountType = accountType;
  }
  
  public String getAccountDesc()
  {
    return this.accountDesc;
  }
  
  public void setAccountDesc(String accountDesc)
  {
    this.accountDesc = accountDesc;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("AccountDetailsFields [accountType=");
    builder.append(this.accountType);
    builder.append(", accountDesc=");
    builder.append(this.accountDesc);
    builder.append(", username=");
    builder.append(this.username);
    builder.append(", password=");
    builder.append(this.password);
    builder.append("]");
    return builder.toString();
  }
}
