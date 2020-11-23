package com.fosun.storekeeper.exception;

/**
 * SK业务异常
 * 
 * @author hengjb
 * @date 2019/08/26
 */
public class StoreKeeperServiceException
  extends RuntimeException
{
  private static final long serialVersionUID = -2577458939187209311L;
  
  public StoreKeeperServiceException() {}
  
  public StoreKeeperServiceException(String message)
  {
    super(message);
  }
  
  public StoreKeeperServiceException(Throwable cause)
  {
    super(cause);
  }
  
  public StoreKeeperServiceException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
