package com.github.tomdom.slick.driver

import slick.driver.{ MySQLDriver => Driver }
import slick.jdbc.JdbcModelBuilder
import slick.jdbc.meta.{ MColumn, MTable }

import scala.concurrent.ExecutionContext

trait MySQLDriver extends Driver {
  override def createModelBuilder(tables: Seq[MTable], ignoreInvalidDefaults: Boolean)(implicit ec: ExecutionContext): JdbcModelBuilder =
    new ModelBuilder(tables, ignoreInvalidDefaults) {
      override def createColumnBuilder(tableBuilder: TableBuilder, meta: MColumn): ColumnBuilder = new ColumnBuilder(tableBuilder, meta) {
        override def default = meta.columnDef.map((_, tpe)).collect {
          case (v, "String") => Some(Some(v))
          case ("1", "Boolean") => Some(Some(true))
          case ("0", "Boolean") => Some(Some(false))
          case ("0000-00-00 00:00:00", "java.sql.Timestamp") => {
            println("matched (\"0000-00-00 00:00:00\", \"java.sql.Timestamp\")")
            None
          }
          case ("0000-00-00", "java.sql.Date") => {
            println("matched (\"0000-00-00\", \"java.sql.Date\")")
            None
          }
        }.getOrElse {
          val d = super.default
          if (meta.nullable == Some(true) && d == None) {
            Some(None)
          } else d
        }

        override def length: Option[Int] = {
          val l = super.length
          if (tpe == "String" && varying && l == Some(65535)) None
          else l
        }

        override def nullable = {
          println(s"super.nullable: ${super.nullable}")
          println(s"meta.columnDef: ${meta.columnDef}")
          println(s"tpe: $tpe")
          println(s"matches: ${meta.columnDef.map(cd => Seq("0000-00-00 00:00:00", "0000-00-00").contains(cd)).getOrElse(false)}")
          println

          super.nullable || meta.columnDef.map(cd => Seq("0000-00-00 00:00:00", "0000-00-00").contains(cd)).getOrElse(false)
        }
      }
    }
}

object MySQLDriver extends MySQLDriver
