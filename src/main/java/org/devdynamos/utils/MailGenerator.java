package org.devdynamos.utils;

import org.devdynamos.contollers.InventoryController;
import org.devdynamos.contollers.ProductsController;
import org.devdynamos.models.*;

import java.util.List;

public class MailGenerator {
    public static String generateInvoice(CustomerOrder order, Customer customer, List<OrderItem> orderItemList){
        String invoice = generateInvoiceHead(customer, order.getGrandTotal()) + generateInvoiceOrderSummary(orderItemList) + generateInvoiceFooter(order);
        return invoice;
    }

    private static String generateInvoiceHead(Customer customer, Double grandTotal){
        String head = "" +
                "<!DOCTYPE HTML\n" +
                "  PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "  <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG/>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "  </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                "  <title></title>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width: 520px) {\n" +
                "      .u-row {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        vertical-align: top;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-22p53 {\n" +
                "        width: 112.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-22p74 {\n" +
                "        width: 113.7px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-23p13 {\n" +
                "        width: 115.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-23p14 {\n" +
                "        width: 115.7px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-50 {\n" +
                "        width: 250px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-54p13 {\n" +
                "        width: 270.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-54p33 {\n" +
                "        width: 271.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-100 {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 520px) {\n" +
                "      .u-row-container {\n" +
                "        max-width: 100% !important;\n" +
                "        padding-left: 0px !important;\n" +
                "        padding-right: 0px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        min-width: 320px !important;\n" +
                "        max-width: 100% !important;\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col>div {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    tr,\n" +
                "    td {\n" +
                "      vertical-align: top;\n" +
                "      border-collapse: collapse;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      margin: 0;\n" +
                "    }\n" +
                "\n" +
                "    .ie-container table,\n" +
                "    .mso-container table {\n" +
                "      table-layout: fixed;\n" +
                "    }\n" +
                "\n" +
                "    * {\n" +
                "      line-height: inherit;\n" +
                "    }\n" +
                "\n" +
                "    a[x-apple-data-detectors='true'] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: none !important;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "\n" +
                "    #u_body a {\n" +
                "      color: #0000ee;\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "  <!--<![endif]-->\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body u_body\"\n" +
                "  style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #ffffff;color: #000000\">\n" +
                "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table id=\"u_body\"\n" +
                "    style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #ffffff;width:100%\"\n" +
                "    cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "      <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #f7fbfc;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "                                    <img align=\"center\" border=\"0\" src=\"https://i.ibb.co/XZFFzxv/shipshapelogo.png\"\n" +
                "                                      alt=\"Cart Icon\" title=\"Cart Icon\"\n" +
                "                                      style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 15%;max-width: 72px;\"\n" +
                "                                      width=\"72\" />\n" +
                "\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-size: 18px; line-height: 25.2px; font-family: Montserrat, sans-serif;\"><strong><span\n" +
                "                                        style=\"line-height: 25.2px; font-size: 18px;\">Thank you for your\n" +
                "                                        purchase!</span></strong></span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Hi " + customer.getCustomerName() + ", we're getting your order ready to be shipped.</span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">We\n" +
                "                                    will notify you when it has been sent.</span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"250\" style=\"background-color: #f7fbfc;width: 250px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-50\"\n" +
                "                  style=\"max-width: 320px;min-width: 250px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong><span\n" +
                "                                      style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Customer\n" +
                "                                      details</span></strong><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">&nbsp;</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + customer
                .getCustomerName() + "</span><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + customer.getContactNumber() + "</span><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + customer.getEmail() + "</span>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"250\" style=\"background-color: #f7fbfc;width: 250px;padding: 0px 0px 29px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-50\"\n" +
                "                  style=\"max-width: 320px;min-width: 250px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px 0px 29px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 2px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong><span\n" +
                "                                      style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Payment\n" +
                "                                      method</span></strong></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\"><em>Cash</em>\n" +
                "                                    &mdash; " + grandTotal + " LKR</span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #ffffff;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>";

        return head;
    }

    private static String generateInvoiceOrderSummary(List<OrderItem> orderItemList){
        String orderSummary = "";

        for(OrderItem orderItem : orderItemList){
            orderSummary +=
                    "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "            <div class=\"u-row\"\n" +
                    "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                    "              <div\n" +
                    "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                    "\n" +
                    "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"115\" style=\"width: 115px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                    "                <div class=\"u-col u-col-23p14\"\n" +
                    "                  style=\"max-width: 320px;min-width: 115.7px;display: table-cell;vertical-align: top;\">\n" +
                    "                  <div\n" +
                    "                    style=\"height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    <div\n" +
                    "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                      <!--<![endif]-->\n" +
                    "\n" +
                    "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                    "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "                        <tbody>\n" +
                    "                          <tr>\n" +
                    "                            <td\n" +
                    "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:arial,helvetica,sans-serif;\"\n" +
                    "                              align=\"left\">\n" +
                    "\n" +
                    "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "                                <tr>\n" +
                    "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                    "\n" +
                    "                                    <img align=\"center\" border=\"0\" src=\"https://i.ibb.co/cJR2gC6/gear.png\" alt=\"Hand Bag\"\n" +
                    "                                      title=\"Hand Bag\"\n" +
                    "                                      style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 85%;max-width: 98.34px;\"\n" +
                    "                                      width=\"98.34\" />\n" +
                    "\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </table>\n" +
                    "\n" +
                    "                            </td>\n" +
                    "                          </tr>\n" +
                    "                        </tbody>\n" +
                    "                      </table>\n" +
                    "\n" +
                    "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    </div><!--<![endif]-->\n" +
                    "                  </div>\n" +
                    "                </div>\n" +
                    "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"271\" style=\"background-color: #ffffff;width: 271px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                    "                <div class=\"u-col u-col-54p33\"\n" +
                    "                  style=\"max-width: 320px;min-width: 271.65px;display: table-cell;vertical-align: top;\">\n" +
                    "                  <div\n" +
                    "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    <div\n" +
                    "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                      <!--<![endif]-->\n" +
                    "\n" +
                    "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                    "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "                        <tbody>\n" +
                    "                          <tr>\n" +
                    "                            <td\n" +
                    "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:30px 10px 66px 20px;font-family:arial,helvetica,sans-serif;\"\n" +
                    "                              align=\"left\">\n" +
                    "\n" +
                    "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                    "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\"><strong>" + orderItem.getName() + " </strong><span\n" +
                    "                                      style=\"color: #666666; font-size: 14px; line-height: 19.6px;\">x" + orderItem.getQuantity() + "</span></span></p>\n" +
                    "                              </div>\n" +
                    "\n" +
                    "                            </td>\n" +
                    "                          </tr>\n" +
                    "                        </tbody>\n" +
                    "                      </table>\n" +
                    "\n" +
                    "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    </div><!--<![endif]-->\n" +
                    "                  </div>\n" +
                    "                </div>\n" +
                    "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"112\" style=\"background-color: #ffffff;width: 112px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                    "                <div class=\"u-col u-col-22p53\"\n" +
                    "                  style=\"max-width: 320px;min-width: 112.65px;display: table-cell;vertical-align: top;\">\n" +
                    "                  <div\n" +
                    "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    <div\n" +
                    "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                      <!--<![endif]-->\n" +
                    "\n" +
                    "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                    "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "                        <tbody>\n" +
                    "                          <tr>\n" +
                    "                            <td\n" +
                    "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:30px 10px 66px 20px;font-family:arial,helvetica,sans-serif;\"\n" +
                    "                              align=\"left\">\n" +
                    "\n" +
                    "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong><span\n" +
                    "                                      style=\"font-family: Montserrat, sans-serif; font-size: 16px; line-height: 22.4px;\">"  + orderItem.getTotal() + " LKR</span></strong>\n" +
                    "                                </p>\n" +
                    "                              </div>\n" +
                    "\n" +
                    "                            </td>\n" +
                    "                          </tr>\n" +
                    "                        </tbody>\n" +
                    "                      </table>\n" +
                    "\n" +
                    "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    </div><!--<![endif]-->\n" +
                    "                  </div>\n" +
                    "                </div>\n" +
                    "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "              </div>\n" +
                    "            </div>\n" +
                    "          </div>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "            <div class=\"u-row\"\n" +
                    "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                    "              <div\n" +
                    "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                    "\n" +
                    "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #ffffff;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                    "                <div class=\"u-col u-col-100\"\n" +
                    "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                    "                  <div\n" +
                    "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    <div\n" +
                    "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                    "                      <!--<![endif]-->\n" +
                    "\n" +
                    "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                    "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "                        <tbody>\n" +
                    "                          <tr>\n" +
                    "                            <td\n" +
                    "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                    "                              align=\"left\">\n" +
                    "\n" +
                    "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                    "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                    "                                <tbody>\n" +
                    "                                  <tr style=\"vertical-align: top\">\n" +
                    "                                    <td\n" +
                    "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                    "                                      <span>&#160;</span>\n" +
                    "                                    </td>\n" +
                    "                                  </tr>\n" +
                    "                                </tbody>\n" +
                    "                              </table>\n" +
                    "\n" +
                    "                            </td>\n" +
                    "                          </tr>\n" +
                    "                        </tbody>\n" +
                    "                      </table>\n" +
                    "\n" +
                    "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                    "                    </div><!--<![endif]-->\n" +
                    "                  </div>\n" +
                    "                </div>\n" +
                    "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "              </div>\n" +
                    "            </div>\n" +
                    "          </div>";
        }

        return orderSummary;
    }

    private static String generateInvoiceFooter(CustomerOrder order){
        double discountAmount = (double) Math.round((order.getTotal() * (order.getDiscountRate() / 100)) * 100) / 100;
        double vatAmount = (double) Math.round(((order.getTotal() - discountAmount) * (order.getVatRate() / 100) * 100)) / 100;
        double serviceChargeAmount = (double) Math.round(((order.getTotal() - discountAmount + vatAmount) * (order.getServiceChargeRate() / 100)) * 100) / 100;
        double total = (double) Math.round(order.getTotal() * 100) / 100;
        double grandTotal = (double) Math.round(order.getGrandTotal() * 100) / 100;

        String footer = " <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"270\" style=\"background-color: #ffffff;width: 270px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-54p13\"\n" +
                "                  style=\"max-width: 320px;min-width: 270.65px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">.</p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"115\" style=\"background-color: #ffffff;width: 115px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-23p13\"\n" +
                "                  style=\"max-width: 320px;min-width: 115.65px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Total</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                  style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Discount</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                  style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">VAT</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                  style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Service Charge</span>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"113\" style=\"background-color: #ffffff;width: 113px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-22p74\"\n" +
                "                  style=\"max-width: 320px;min-width: 113.7px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + total + "</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + discountAmount + " LKR</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                  style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + vatAmount + " LKR</span>\n" +
                "                              </p>\n" +
                "                              <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">" + serviceChargeAmount + " LKR</span>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #ffffff;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"47%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"270\" style=\"background-color: #ffffff;width: 270px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-54p13\"\n" +
                "                  style=\"max-width: 320px;min-width: 270.65px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 11px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\">.</p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"115\" style=\"background-color: #ffffff;width: 115px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-23p13\"\n" +
                "                  style=\"max-width: 320px;min-width: 115.65px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 11px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong><span\n" +
                "                                      style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Grand Total</span></strong>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"113\" style=\"background-color: #ffffff;width: 113px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-22p74\"\n" +
                "                  style=\"max-width: 320px;min-width: 113.7px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 11px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-size: 16px; line-height: 22.4px;\"><strong><span\n" +
                "                                        style=\"font-family: Montserrat, sans-serif; line-height: 22.4px; font-size: 16px;\">" + grandTotal+ " LKR</span></strong></span>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #f7fbfc;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">If\n" +
                "                                    you have any questions, reply to this</span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">email\n" +
                "                                    or <strong>contact us at </strong>info.shipshapemanager@gmail.com</span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\"></span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 40px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div align=\"center\">\n" +
                "                                <div style=\"display: table; max-width:147px;\">\n" +
                "                                  <!--[if (mso)|(IE)]><table width=\"147\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse;\" align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:147px;\"><tr><![endif]-->\n" +
                "\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 5px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\"\n" +
                "                                    height=\"32\"\n" +
                "                                    style=\"width: 32px !important;height: 32px !important;display: inline-block;border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 5px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"center\" valign=\"middle\"\n" +
                "                                          style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://facebook.com/\" title=\"Facebook\" target=\"_blank\">\n" +
                "                                            <img src=\"https://i.ibb.co/WGjTyvM/image-2.png\" alt=\"Facebook\" title=\"Facebook\" width=\"32\"\n" +
                "                                              style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 5px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\"\n" +
                "                                    height=\"32\"\n" +
                "                                    style=\"width: 32px !important;height: 32px !important;display: inline-block;border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 5px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"center\" valign=\"middle\"\n" +
                "                                          style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://twitter.com/\" title=\"Twitter\" target=\"_blank\">\n" +
                "                                            <img src=\"https://i.ibb.co/qJJ4kBf/image-1.png\" alt=\"Twitter\" title=\"Twitter\" width=\"32\"\n" +
                "                                              style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 5px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\"\n" +
                "                                    height=\"32\"\n" +
                "                                    style=\"width: 32px !important;height: 32px !important;display: inline-block;border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 5px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"center\" valign=\"middle\"\n" +
                "                                          style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://linkedin.com/\" title=\"LinkedIn\" target=\"_blank\">\n" +
                "                                            <img src=\"https://i.ibb.co/LzHh3pd/image-3.png\" alt=\"LinkedIn\" title=\"LinkedIn\" width=\"32\"\n" +
                "                                              style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\"\n" +
                "                                    height=\"32\"\n" +
                "                                    style=\"width: 32px !important;height: 32px !important;display: inline-block;border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 0px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"center\" valign=\"middle\"\n" +
                "                                          style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://instagram.com/\" title=\"Instagram\" target=\"_blank\">\n" +
                "                                            <img src=\"https://i.ibb.co/Wv81mSX/image-4.png\" alt=\"Instagram\" title=\"Instagram\" width=\"32\"\n" +
                "                                              style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                                </div>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "  <!--[if mso]></div><![endif]-->\n" +
                "  <!--[if IE]></div><![endif]-->\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        return footer;
    }

    public static String generateAllocationNotification(Employee employee, ServiceJob serviceJob){
        String notification = generateAllocationNotificationHeader(employee, serviceJob);
        return notification;
    }

    private static String generateAllocationNotificationHeader(Employee employee, ServiceJob serviceJob){
        String head = "" +
                "<!DOCTYPE HTML\n" +
                "  PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "  <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG/>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "  </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                "  <title></title>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width: 520px) {\n" +
                "      .u-row {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        vertical-align: top;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-22p53 {\n" +
                "        width: 112.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-22p74 {\n" +
                "        width: 113.7px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-23p13 {\n" +
                "        width: 115.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-23p14 {\n" +
                "        width: 115.7px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-50 {\n" +
                "        width: 250px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-54p13 {\n" +
                "        width: 270.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-54p33 {\n" +
                "        width: 271.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-100 {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 520px) {\n" +
                "      .u-row-container {\n" +
                "        max-width: 100% !important;\n" +
                "        padding-left: 0px !important;\n" +
                "        padding-right: 0px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        min-width: 320px !important;\n" +
                "        max-width: 100% !important;\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col>div {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    tr,\n" +
                "    td {\n" +
                "      vertical-align: top;\n" +
                "      border-collapse: collapse;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      margin: 0;\n" +
                "    }\n" +
                "\n" +
                "    .ie-container table,\n" +
                "    .mso-container table {\n" +
                "      table-layout: fixed;\n" +
                "    }\n" +
                "\n" +
                "    * {\n" +
                "      line-height: inherit;\n" +
                "    }\n" +
                "\n" +
                "    a[x-apple-data-detectors='true'] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: none !important;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "\n" +
                "    #u_body a {\n" +
                "      color: #0000ee;\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "  <!--<![endif]-->\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body u_body\"\n" +
                "  style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #ffffff;color: #000000\">\n" +
                "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table id=\"u_body\"\n" +
                "    style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #ffffff;width:100%\"\n" +
                "    cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "      <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #f7fbfc;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "                                    <img align=\"center\" border=\"0\" src=\"https://i.ibb.co/XZFFzxv/shipshapelogo.png\"\n" +
                "                                      alt=\"Cart Icon\" title=\"Cart Icon\"\n" +
                "                                      style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 15%;max-width: 72px;\"\n" +
                "                                      width=\"72\" />\n" +
                "\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-size: 18px; line-height: 25.2px; font-family: Montserrat, sans-serif;\"><strong><span\n" +
                "                                        style=\"line-height: 25.2px; font-size: 18px;\">You are allocated to the " + serviceJob.getServiceName() + " (" + serviceJob.getServiceJobId() + ")" + "</span></strong></span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Hi " + employee.getEmpName() + ", be advised that you are allocated to the subjected job.</span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +

                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong><span\n" +
                "                                      style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Job\n" +
                "                                      details</span></strong><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">&nbsp;</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Job ID : " + serviceJob.getServiceJobId() + "</span><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Service Name : " + serviceJob.getServiceName() + "</span><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Order : " + serviceJob.getCustomerOrderCaption() + "</span>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"250\" style=\"background-color: #f7fbfc;width: 250px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-50\"\n" +
                "                  style=\"max-width: 320px;min-width: 250px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"250\" style=\"background-color: #f7fbfc;width: 250px;padding: 0px 0px 29px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-50\"\n" +
                "                  style=\"max-width: 320px;min-width: 250px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px 0px 29px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #ffffff;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>";

        return head;
    }

    public static String generateJobCompletionNotification(Customer customer, ServiceJob serviceJob){
        return generateJobCompletionNotificationHeader(customer, serviceJob);
    }

    private static String generateJobCompletionNotificationHeader(Customer customer, ServiceJob serviceJob){
        String head = "" +
                "<!DOCTYPE HTML\n" +
                "  PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "  <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG/>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "  </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                "  <title></title>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width: 520px) {\n" +
                "      .u-row {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        vertical-align: top;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-22p53 {\n" +
                "        width: 112.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-22p74 {\n" +
                "        width: 113.7px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-23p13 {\n" +
                "        width: 115.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-23p14 {\n" +
                "        width: 115.7px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-50 {\n" +
                "        width: 250px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-54p13 {\n" +
                "        width: 270.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-54p33 {\n" +
                "        width: 271.65px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col-100 {\n" +
                "        width: 500px !important;\n" +
                "      }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 520px) {\n" +
                "      .u-row-container {\n" +
                "        max-width: 100% !important;\n" +
                "        padding-left: 0px !important;\n" +
                "        padding-right: 0px !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row .u-col {\n" +
                "        min-width: 320px !important;\n" +
                "        max-width: 100% !important;\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-row {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .u-col>div {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    tr,\n" +
                "    td {\n" +
                "      vertical-align: top;\n" +
                "      border-collapse: collapse;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      margin: 0;\n" +
                "    }\n" +
                "\n" +
                "    .ie-container table,\n" +
                "    .mso-container table {\n" +
                "      table-layout: fixed;\n" +
                "    }\n" +
                "\n" +
                "    * {\n" +
                "      line-height: inherit;\n" +
                "    }\n" +
                "\n" +
                "    a[x-apple-data-detectors='true'] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: none !important;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "\n" +
                "    #u_body a {\n" +
                "      color: #0000ee;\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "  <!--<![endif]-->\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body u_body\"\n" +
                "  style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #ffffff;color: #000000\">\n" +
                "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table id=\"u_body\"\n" +
                "    style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #ffffff;width:100%\"\n" +
                "    cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "      <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #f7fbfc;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "                                    <img align=\"center\" border=\"0\" src=\"https://i.ibb.co/XZFFzxv/shipshapelogo.png\"\n" +
                "                                      alt=\"Cart Icon\" title=\"Cart Icon\"\n" +
                "                                      style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 15%;max-width: 72px;\"\n" +
                "                                      width=\"72\" />\n" +
                "\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-size: 18px; line-height: 25.2px; font-family: Montserrat, sans-serif;\"><strong><span\n" +
                "                                        style=\"line-height: 25.2px; font-size: 18px;\">The ordered " + serviceJob.getServiceName() + " (" + serviceJob.getServiceJobId() + ") service is now completed." + "</span></strong></span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <div\n" +
                "                                style=\"font-size: 14px; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Hi " + customer.getCustomerName() + ", be advised that your service order(s) are now finished.</span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +

                "                              <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong><span\n" +
                "                                      style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Job\n" +
                "                                      details</span></strong><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">&nbsp;</span>\n" +
                "                                </p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Job ID : " + serviceJob.getServiceJobId() + "</span><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Service Name : " + serviceJob.getServiceName() + "</span><br /><span\n" +
                "                                    style=\"font-family: Montserrat, sans-serif; font-size: 14px; line-height: 19.6px;\">Order : " + serviceJob.getCustomerOrderCaption() + "</span>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"250\" style=\"background-color: #f7fbfc;width: 250px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-50\"\n" +
                "                  style=\"max-width: 320px;min-width: 250px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"250\" style=\"background-color: #f7fbfc;width: 250px;padding: 0px 0px 29px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-50\"\n" +
                "                  style=\"max-width: 320px;min-width: 250px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #f7fbfc;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px 0px 29px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\"\n" +
                "              style=\"margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div\n" +
                "                style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color: #ffffff;width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\"\n" +
                "                  style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div\n" +
                "                    style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div\n" +
                "                      style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                        cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;\"\n" +
                "                              align=\"left\">\n" +
                "\n" +
                "                              <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 2px solid #e7e7e7;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <tbody>\n" +
                "                                  <tr style=\"vertical-align: top\">\n" +
                "                                    <td\n" +
                "                                      style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                      <span>&#160;</span>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div><!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>";

        return head;
    }
}
