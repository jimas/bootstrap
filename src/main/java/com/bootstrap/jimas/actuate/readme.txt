以下是所有监控描述：

HTTP方法  路径                                描述                                                                                                                                                                             鉴权 
GET      /autoconfig   查看自动配置的使用情况，该报告展示所有auto-configuration候选者及它们被应用或未被应用的原因                     true
GET      /configprops  显示一个所有@ConfigurationProperties的整理列表                                                                 		           true
GET      /beans        显示一个应用中所有Spring Beans的完整列表                                                       					           true
GET      /dump         打印线程栈                                                                                            										   true
GET      /env          查看所有环境变量                                                                               								           true
GET      /env/{name}   查看具体变量值                                                                                 								           true
GET      /health       查看应用健康指标                                                                      							                   false
GET      /info         查看应用信息                                                                             								               false
GET      /mappings     查看所有url映射                                                                          	  								           true
GET      /metrics      查看应用基本指标                                                                         								               true
GET      /metrics/{name} 查看具体指标                                                                               									           true
POST     /shutdown        允许应用以优雅的方式关闭（默认情况下不启用）                   			                               true
GET      /trace            查看基本追踪信息                                                                							                   true



可以检查的其他一些情况的健康信息。下面的HealthIndicators会被Spring Boot自动配置：

DiskSpaceHealthIndicator     低磁盘空间检测
DataSourceHealthIndicator  检查是否能从DataSource获取连接
MongoHealthIndicator   检查一个Mongo数据库是否可用（up）
RabbitHealthIndicator   检查一个Rabbit服务器是否可用（up）
RedisHealthIndicator      检查一个Redis服务器是否可用（up）
SolrHealthIndicator  检查一个Solr服务器是否可用（up）