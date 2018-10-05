using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using PolliteBackend.Models;
using Microsoft.EntityFrameworkCore;
using Pomelo.EntityFrameworkCore.MySql.Infrastructure;

namespace PolliteBackend
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);

            // TODO: Change this in production to a production database (use conditions to check if we are dev or not)
            services.AddDbContext<PollContext>(options => options.UseMySql(Configuration.GetConnectionString("PolliteTest"),
                mysqlOptions => { mysqlOptions.ServerVersion(new Version(5, 7, 17), ServerType.MariaDb); }));
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }

            app.UseHttpsRedirection();

            // TODO: fix routes.
            app.UseMvc(routes => {
                
                routes.MapRoute(
                    name: "api",
                    template: "api/{controller=NotFound}/{action=Index}"
                );

                routes.MapRoute(
                    name: "default",
                    template: "{controller=NotFound}/{action=Index}"
                );
            });
        }
    }
}
